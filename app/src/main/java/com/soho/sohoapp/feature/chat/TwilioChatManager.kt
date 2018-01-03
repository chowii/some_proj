package com.soho.sohoapp.feature.chat

import android.content.Context
import android.util.Log
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.SohoApplication
import com.twilio.chat.*
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 28/12/17.
 */
class TwilioChatManager {

    companion object {

        private var chatClient: ChatClient? = null
        private var chatConversation: Channel? = null
        private var twilioLastMessageList: List<Message>? = null

        fun getChatClient(context: Context): Observable<ChatClient> {

            return Observable.create { emission ->
                Log.d("LOG_TAG---", "twilioChatClient is null: ${chatClient == null}")

                chatClient?.let {
                    emission.onNext(it)
                    return@create
                }

                val chatBuilder = ChatClient.Properties.Builder().createProperties()
                val twilioToken = Dependencies.DEPENDENCIES.userPrefs.twilioToken
                ChatClient.create(context, twilioToken, chatBuilder, object : CallbackListener<ChatClient>() {
                    override fun onSuccess(chatClient: ChatClient) {
                        this@Companion.chatClient = chatClient
                        emission.onNext(chatClient)
                    }

                    override fun onError(errorInfo: ErrorInfo) {
                        super.onError(errorInfo)
                        emission.onError(Throwable(errorInfo.message))
                    }
                })
            }
        }

        fun getChatConversation(channelSid: String): Observable<Channel> {
            return Observable.create { emission ->
                Log.d("LOG_TAG---", "twilioChatChannel is null: ${chatConversation == null}")

                chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                    override fun onSuccess(channel: Channel) {
                        chatConversation = channel
                        emission.onNext(channel)
                    }

                    override fun onError(errorInfo: ErrorInfo?) {
                        Log.e("LOG_TAG---", "onError getChatConversation: " + errorInfo?.message)
                        emission.onError(Throwable(errorInfo?.message))
                        super.onError(errorInfo)
                    }
                })
            }
        }

        fun getLastMessageList(lastMessageCount: Int): Observable<List<Message>> {
            return Observable.create { emission ->
                chatConversation?.apply {
                    messages.getLastMessages(lastMessageCount, object : CallbackListener<List<Message>>() {
                        override fun onSuccess(messageList: List<Message>) {
                            twilioLastMessageList = messageList
                            emission.onNext(messageList)
                        }

                        override fun onError(errorInfo: ErrorInfo?) {
                            Log.e("LOG_TAG---", "onError getChatConversation: " + errorInfo?.message)
                            emission.onError(Throwable(errorInfo?.message))
                            super.onError(errorInfo)
                        }
                    })
                }
            }
        }

        fun sendMessageToChannel(channelSid: String, messageText: String, onMessageSent: (Message) -> Unit = {}): Observable<Message> {
            return Observable.create { emission ->
                if (chatConversation == null) {
                    if (chatClient == null) {
                        getChatClient(SohoApplication.getContext())
                                .switchMap { getChatConversation(channelSid) }
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        { sendMessage(it, messageText, onMessageSent, emission) },
                                        { emission.onError(it) }
                                )
                    } else
                        getChatConversation(channelSid)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        { sendMessage(it, messageText, onMessageSent, emission) },
                                        { emission.onError(it) }
                                )
                } else chatConversation?.let { sendMessage(it, messageText, onMessageSent, emission) }
            }
        }

        private fun sendMessage(channel: Channel, messageText: String, onMessageSent: (Message) -> Unit, emission: ObservableEmitter<Message>) {
            channel.messages.sendMessage(
                    Message.options().withBody(messageText),
                    object : CallbackListener<Message>() {
                        override fun onSuccess(message: Message) {
                            onMessageSent(message)
                            emission.onNext(message)
                        }

                        override fun onError(errorInfo: ErrorInfo) {
                            emission.onError(Throwable(errorInfo.message))
                            super.onError(errorInfo)
                        }
                    })
        }

    }
}

