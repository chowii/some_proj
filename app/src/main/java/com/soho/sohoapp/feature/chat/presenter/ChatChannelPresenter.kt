package com.soho.sohoapp.feature.chat.presenter

import android.content.Context
import android.support.annotation.StringRes
import android.util.Log
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.ChatAccessManager
import com.soho.sohoapp.feature.chat.TwilioChatManager
import com.soho.sohoapp.feature.chat.adapter.ChatClientListenerAdapter
import com.soho.sohoapp.feature.chat.contract.ChatChannelContract
import com.soho.sohoapp.feature.chat.model.ChatChannel
import com.soho.sohoapp.feature.common.model.EmptyDataSet
import com.twilio.accessmanager.AccessManager
import com.twilio.chat.ChatClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 21/12/17.
 */
class ChatChannelPresenter(private val context: Context?,
                           private val interactor: ChatChannelContract.ViewInteractable
) : ChatChannelContract.ViewPresentable {

    private var chatAccessManager: ChatAccessManager? = null

    override fun startPresenting() {
        interactor.showLoading()
        getChatChannelList()
    }

    override fun getChatChannelList() {
        context?.let {

            TwilioChatManager.getChatClient(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { client ->
                                client.setListener(object : ChatClientListenerAdapter() {
                                    override fun onClientSynchronization(status: ChatClient.SynchronizationStatus) {
                                        if (status == ChatClient.SynchronizationStatus.COMPLETED) {
                                            // Client is now ready for business, start working
                                            addAccessTokenManager()

                                            addSubscribedChannelListToAdapter(client, it)
                                        }
                                    }
                                })

                            },
                            { errorInfo ->
                                Log.d("LOG_TAG---", "Twilio error: " + errorInfo?.message)
                                interactor.showError(Throwable("Access Token expired"))
                                interactor.hideLoading()
                            })
        }

    }

    private fun addSubscribedChannelListToAdapter(client: ChatClient, context: Context) {
        client.channels.subscribedChannels?.let { channelList ->

            if (channelList.isEmpty()) {
                interactor.run {
                    updateChannelList(
                                EmptyDataSet(
                                        getString(context, R.string.empty_state_chat_title),
                                        getString(context, R.string.empty_state_chat_message),
                                        getString(context, R.string.empty_state_chat_button_text))
                    )
                    hideLoading()
                }
            } else {

                val chatChannel = channelList.map { ChatChannel(it) }.sortedBy { it.lastMessage?.timeStampAsDate?.time }

                chatChannel.map { chat ->

                    chat.getLastMessageObservable()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    {
                                        chat.lastMessage = it.firstOrNull()
                                        interactor.updateChannelList(chat)
                                        interactor.hideLoading()
                                    },
                                    {
                                        Log.d("LOG_TAG---", "message error: " + it.message)
                                        interactor.hideLoading()
                                    })
                }
            }
        }
    }

    private fun getString(context: Context, @StringRes res: Int) = context.getString(res)

    override fun stopPresenting() {
        chatAccessManager?.clearDisposable()
    }

    private fun addAccessTokenManager() {
        chatAccessManager = ChatAccessManager()
        val accessManager = AccessManager(DEPENDENCIES.userPrefs.twilioToken, chatAccessManager)
        accessManager.addTokenUpdateListener(chatAccessManager)
    }
}