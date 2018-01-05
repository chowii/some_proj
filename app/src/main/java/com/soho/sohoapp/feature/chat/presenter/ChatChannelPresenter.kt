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
import com.soho.sohoapp.preferences.UserPrefs
import com.twilio.accessmanager.AccessManager
import com.twilio.chat.ChatClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 21/12/17.
 */
class ChatChannelPresenter(private val context: Context?,
                           private val view: ChatChannelContract.ViewInteractable,
                           private val userPrefs: UserPrefs
) : ChatChannelContract.ViewPresentable {

    override fun startPresenting() {
        view.showLoading()
        getChatChannelList()
    }

    private val compositeDisposable = CompositeDisposable()

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

                                            addSubscribedChannelListToAdapter(it, client)
                                        }
                                    }
                                })

                            },
                            { errorInfo ->
                                Log.d("LOG_TAG---", "Twilio error: " + errorInfo?.message)
                                view.showError(Throwable("Access Token expired"))
                                view.hideLoading()
                            })
        }

    }

    private fun addSubscribedChannelListToAdapter(context: Context, client: ChatClient) {
        client.channels.subscribedChannels?.let { channelList ->

            if (channelList.isEmpty()) {
                view.run {
                    updateChannelList(
                            EmptyDataSet(
                                    getString(context, R.string.empty_state_chat_title),
                                    getString(context, R.string.empty_state_chat_message),
                                    getString(context, R.string.empty_state_chat_button_text))
                    )
                    hideLoading()
                }
            } else {

                val chatChannel = channelList.map { ChatChannel(it) }.sortedBy { it.messageList.firstOrNull()?.timeStampAsDate?.time }

                chatChannel.map { chat ->

                    compositeDisposable.add(chat.getLastMessageObservable()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    {
                                        chat.messageList = it
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
    }

    private fun getString(context: Context, @StringRes res: Int) = context.getString(res)


    private fun addAccessTokenManager() {
        val chatAccessManager = ChatAccessManager(DEPENDENCIES.userPrefs)
        val accessManager = AccessManager(userPrefs.twilioToken, chatAccessManager)
        accessManager.addTokenUpdateListener(chatAccessManager)
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }
}