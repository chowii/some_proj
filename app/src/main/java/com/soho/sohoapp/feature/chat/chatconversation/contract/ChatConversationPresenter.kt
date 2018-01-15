package com.soho.sohoapp.feature.chat.chatconversation.contract

import android.content.Context
import android.net.Uri
import android.util.Log
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.feature.chat.model.ChatConversation
import com.soho.sohoapp.feature.chat.model.ChatMessage
import com.soho.sohoapp.utils.FileHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Created by chowii on 22/12/17.
 */
class ChatConversationPresenter(private val context: Context,
                                private val view: ChatConversationContract.ViewInteractable,
                                private val channelSid: String) : ChatConversationContract.ViewPresenter {

    private val compositeDisposable = CompositeDisposable()
    private val numberOfLastMessages = 35
    private var conversationList: List<ChatConversation> = arrayListOf()
    private var chatConversation: ChatConversation? = null

    override fun startPresenting() {
        view.showLoading()
        getChatConversation()
    }

    override fun getChatConversation() {
        DEPENDENCIES.sohoService.getAllConversations()
                .switchMap {
                    conversationList = it
                    chatConversation = it.find { it.channelSid == channelSid }
                    DEPENDENCIES.twilioManager.getChatMessages(chatConversation?.channelSid.orEmpty(), numberOfLastMessages)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            val messageList = it.map { ChatMessage(it, chatConversation) }.toMutableList()
                            view.configureAdapter(messageList)
                            view.hideLoading()
                        },
                        {
                            Log.d("LOG_TAG---", "Twilio error: " + it?.message)
                            view.showError(it)
                            view.hideLoading()
                        }
                )
    }

    override fun uploadGalleryImageFromIntent(uri: Uri) {
        val imageByteArray = FileHelper.newInstance(context).compressPhoto(uri)

        val chatId = conversationList.find { it.channelSid == channelSid }
        DEPENDENCIES.sohoService.attachToChat(createMultipart(imageByteArray).build(), chatId?.id ?: 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            //TODO append media message to chat
                            it.file
                        },
                        {
                            Log.d("LOG_TAG---", "send media: ${it.message} ")
                        }
                )
    }


    private fun createMultipart(byteArray: ByteArray) = MultipartBody.Builder()
            .apply {
                setType(MultipartBody.FORM)
                addFormDataPart("file", "soho", createRequestBody(byteArray))
            }

    private fun createRequestBody(data: ByteArray?) = RequestBody.create(
            MediaType.parse("image/jpeg"),
            data
    )

    override fun stopPresenting() {
        compositeDisposable.clear()
    }
}