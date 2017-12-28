package com.soho.sohoapp.feature.chat

import android.content.Context
import android.util.Log
import com.soho.sohoapp.Dependencies
import com.twilio.chat.*
import io.reactivex.Observable

/**
 * Created by chowii on 28/12/17.
 */
class TwilioChatManager {

    companion object {

        private var twilioChatClient: ChatClient? = null
        private var twilioChatChannel: Channel? = null
        private var twilioLastMessageList: List<Message>? = null

        fun getChatClient(context: Context): Observable<ChatClient> {

            return Observable.create { emission ->
                Log.d("LOG_TAG---", "twilioChatClient is null: ${twilioChatClient == null}")

                twilioChatClient?.let {
                    emission.onNext(it)
                    return@create
                }

                val chatBuilder = ChatClient.Properties.Builder().createProperties()
                val twilioToken = Dependencies.DEPENDENCIES.userPrefs.twilioToken
                ChatClient.create(context, twilioToken, chatBuilder, object : CallbackListener<ChatClient>() {
                    override fun onSuccess(chatClient: ChatClient) {
                        twilioChatClient = chatClient
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
                Log.d("LOG_TAG---", "twilioChatChannel is null: ${twilioChatChannel == null}")

                twilioChatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                    override fun onSuccess(channel: Channel) {
                        twilioChatChannel = channel
                        emission.onNext(channel)
                    }

                })
            }
        }

        fun getLastMessageList(lastMessageCount: Int): Observable<List<Message>> {
            return Observable.create { emission ->
                twilioChatChannel?.apply {
                    messages.getLastMessages(lastMessageCount, object : CallbackListener<List<Message>>() {
                        override fun onSuccess(messageList: List<Message>) {
                            twilioLastMessageList = messageList
                            emission.onNext(messageList)
                        }
                    })
                }
            }
        }
    }
}

