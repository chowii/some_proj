package com.soho.sohoapp.feature.chat.chatconversation.contract

import android.content.Context
import android.net.Uri
import android.util.Log
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.TwilioChatManager
import com.soho.sohoapp.feature.chat.adapter.ChatChannelListenerAdapter
import com.soho.sohoapp.feature.chat.model.ChatConversation
import com.soho.sohoapp.feature.chat.model.ChatMessage
import com.soho.sohoapp.navigator.RequestCode.CHAT_CAMERA_PERMISSION
import com.soho.sohoapp.network.Keys.ChatImage.CHAT_ATTACH_IMAGE
import com.soho.sohoapp.network.Keys.ChatImage.CHAT_ATTACH_IMAGE_FILE_NAME
import com.soho.sohoapp.permission.PermissionManagerImpl
import com.soho.sohoapp.utils.DateUtils
import com.soho.sohoapp.utils.FileHelper
import com.twilio.chat.Member
import com.twilio.chat.Message
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*

/**
 * Created by chowii on 22/12/17.
 */
class ChatConversationPresenter(private val context: Context,
                                private val view: ChatConversationContract.ViewInteractable,
                                private val channelSid: String,
                                private val rxCamera: Maybe<Uri>,
                                private val twilioManager: TwilioChatManager,
                                private val permissionManager: PermissionManagerImpl
) : ChatConversationContract.ViewPresenter {

    private val compositeDisposable = CompositeDisposable()
    private val imageDisposable = CompositeDisposable()
    private val numberOfLastMessages = 35
    private var chatConversation: ChatConversation? = null

    override fun startPresenting() {
        view.showLoading()
        getChatConversation()
        twilioManager.initChannel(channelSid)
    }

    override fun pickImageFromGallery() {
        view.showLoading()
        view.pickImage()
    }

    override fun takeImageWithCamera() {
        if (permissionManager.hasCameraPermission()) {
            view.showLoading()
            view.captureImage()
        } else {
            cleanImageDisposable()
            imageDisposable.add(permissionManager.requestCameraPermission(CHAT_CAMERA_PERMISSION)
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap {
                        if (it.isPermissionGranted) {
                            view.captureImage()
                            rxCamera.toObservable()
                        } else
                            Observable.empty<Uri>()
                    }.subscribe(
                            {
                                imageDisposable.clear()
                                view.showLoading()
                                uploadGalleryImageFromIntent(
                                        it,
                                        String.format(
                                                Locale.getDefault(),
                                                context.getString(R.string.photo_filename_format),
                                                DateUtils.getDateFormatForFileName())
                                )
                            },
                            {
                                view.showError(it)
                                Log.d("LOG_TAG---", "${it.message}: ")
                            }
                    ))
        }
    }

    override fun getChatConversation() {

        val channelChangeListener: ChatChannelListenerAdapter = object : ChatChannelListenerAdapter() {
            override fun onMessageAdded(message: Message) {
                if (message.attributes.isNull(CHAT_ATTACH_IMAGE))
                    view.appendMessage(ChatMessage(message, chatConversation))
                else
                    view.updateImageMessage(ChatMessage(message, chatConversation))
                super.onMessageAdded(message)
            }

            override fun onTypingStarted(member: Member) {
                view.typingStarted(member)
                super.onTypingStarted(member)
            }

            override fun onTypingEnded(member: Member) {
                view.typingEnded(member)
                super.onTypingEnded(member)
            }
        }
        compositeDisposable.add(DEPENDENCIES.sohoService.getChatConversation(channelSid)
                .switchMap {
                    chatConversation = it
                    view.showAvatar(it.conversionUsers[1].user.avatar?.imageUrl)
                    twilioManager.getChatMessages(channelSid, numberOfLastMessages)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            val messageList = it.map { message ->
                                message.channel.addListener(channelChangeListener)
                                ChatMessage(message, chatConversation)
                            }.toMutableList()
                            view.configureAdapter(messageList)
                            view.hideLoading()
                        },
                        {
                            Log.d("LOG_TAG---", "Twilio error: " + it?.message)
                            view.showError(it)
                            view.hideLoading()
                        }
                ))
    }

    override fun uploadGalleryImageFromIntent(uri: Uri, filename: String) {
        val imageByteArray = FileHelper.newInstance(context).compressPhoto(uri)

        compositeDisposable.add(DEPENDENCIES.sohoService.attachToChat(
                createMultipart(imageByteArray, filename).build(),
                chatConversation?.id ?: 0
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { view.hideLoading() },
                        {
                            view.hideLoading()
                            view.notifyUploadFailed(Pair(uri, filename))
                            Log.d("LOG_TAG---", "send media: ${it.message} ")
                        }
                ))
    }

    override fun startedTyping() = DEPENDENCIES.twilioManager.notifyTypeStarted()

    private fun createMultipart(byteArray: ByteArray, filename: String) = MultipartBody.Builder()
            .apply {
                setType(MultipartBody.FORM)
                addFormDataPart(
                        CHAT_ATTACH_IMAGE,
                        filename,
                        createRequestBody(byteArray)
                )
                addFormDataPart(
                        CHAT_ATTACH_IMAGE_FILE_NAME,
                        filename
                )
            }

    private fun createRequestBody(data: ByteArray?) = RequestBody.create(
            MediaType.parse("image/jpeg"),
            data
    )

    override fun cleanImageDisposable() {
        imageDisposable.clear()
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }
}