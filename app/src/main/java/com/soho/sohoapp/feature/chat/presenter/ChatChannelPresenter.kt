package com.soho.sohoapp.feature.chat.presenter

import android.content.Context
import android.util.Log
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.feature.chat.ChatAccessManager
import com.soho.sohoapp.feature.chat.ChatClientListenerAdapter
import com.soho.sohoapp.feature.chat.contract.ChatChannelContract
import com.soho.sohoapp.feature.chat.model.ChatChannel
import com.twilio.accessmanager.AccessManager
import com.twilio.chat.CallbackListener
import com.twilio.chat.ChatClient
import com.twilio.chat.ErrorInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 21/12/17.
 */
class ChatChannelPresenter(private val context: Context?,
                           private val view: ChatChannelContract.ViewInteractable,
                           private val chatBuilder: ChatClient.Properties
) : ChatChannelContract.ViewPresentable {

    override fun startPresenting() {
        view.showLoading()
        getChatChannelList()
    }

    private val compositeDisposable = CompositeDisposable()

    override fun getChatChannelList() {

        val twilioToken = DEPENDENCIES.userPrefs.twilioToken
        context?.let {

            ChatClient.create(context, twilioToken, chatBuilder, object : CallbackListener<ChatClient>() {
                override fun onSuccess(chatClient: ChatClient?) {

                    chatClient?.let { client ->

                        client.setListener(object : ChatClientListenerAdapter() {
                            override fun onClientSynchronization(status: ChatClient.SynchronizationStatus) {
                                if (status == ChatClient.SynchronizationStatus.COMPLETED) {
                                    // Client is now ready for business, start working
                                    addAccessTokenManager()

                                    addSubscribedChannelListToAdapter(client)
                                }
                            }
                        })
                    }
                }

                override fun onError(errorInfo: ErrorInfo?) {
                    Log.d("LOG_TAG---", "Twilio error: " + errorInfo?.message)
                    super.onError(errorInfo)
                    view.showError(Throwable("Access Token expired"))
                    view.hideLoading()
                }
            })
        }
    }

    private fun addSubscribedChannelListToAdapter(client: ChatClient) {
        client.channels.subscribedChannels?.let { channelList ->

            val chatChannel = channelList.map { ChatChannel(it) }.sortedBy { it.lastMessage?.timeStampAsDate?.time }
            chatChannel.map { chat ->

               compositeDisposable.add(chat.getLastMessageObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    chat.lastMessage = it.firstOrNull()
                                    view.updateChannelList(chat)
                                    view.hideLoading()
                                },
                                {
                                    Log.d("LOG_TAG---", "message error: " + it.message)
                                    view.hideLoading()
                                }))
            }
        }
    }

    private fun addAccessTokenManager() {
        val chatAccessManager = ChatAccessManager()
        val accessManager = AccessManager(DEPENDENCIES.userPrefs.twilioToken, chatAccessManager)
        accessManager.addTokenUpdateListener(chatAccessManager)
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }
}