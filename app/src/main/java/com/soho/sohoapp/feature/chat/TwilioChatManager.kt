package com.soho.sohoapp.feature.chat

import android.content.Context
import android.util.Log
import com.soho.sohoapp.preferences.UserPrefs
import com.twilio.chat.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by chowii on 28/12/17.
 */
class TwilioChatManager {

    private val chatBehaviourSubject: BehaviorSubject<ChatClient> = BehaviorSubject.create()
    var chatObservable: Observable<ChatClient> = chatBehaviourSubject.toFlowable(BackpressureStrategy.ERROR).toObservable()
    private lateinit var chatClient: ChatClient

    fun initChatClient(context: Context, userPrefs: UserPrefs): TwilioChatManager {
        if (!this::chatClient.isInitialized) {
            val chatBuilder = ChatClient.Properties.Builder().createProperties()
            ChatClient.create(context, userPrefs.twilioToken, chatBuilder, object : CallbackListener<ChatClient>() {
                override fun onSuccess(client: ChatClient) {
                    chatClient = client
                    chatBehaviourSubject.onNext(chatClient)
                }

                override fun onError(errorInfo: ErrorInfo) {
                    chatBehaviourSubject.onError(Throwable(errorInfo.message))
                    Log.d("LOG_TAG---", "${errorInfo.message}: ")
                    super.onError(errorInfo)
                }
            })
        }
        return this
    }

    fun getLastMessageList(channelSid: String, numberOfLastMessages: Int): Observable<List<Message>> {
        return Observable.create { emitter ->
            chatClient.channels.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    channel.apply {

                        messages.getLastMessages(numberOfLastMessages, object : CallbackListener<List<Message>>() {
                            override fun onSuccess(messageList: List<Message>) {
                                messageList.map { }
                                emitter.onNext(messageList)
                            }

                            override fun onError(errorInfo: ErrorInfo?) {
                                Log.e("LOG_TAG---", "onError getChatConversation: " + errorInfo?.message)
                                emitter.onError(Throwable(errorInfo?.message))
                                super.onError(errorInfo)
                            }
                        })
                    }
                }

                override fun onError(errorInfo: ErrorInfo?) {
                    Log.e("LOG_TAG---", "onError getChatConversation: " + errorInfo?.message)
                    emitter.onError(Throwable(errorInfo?.message))
                    super.onError(errorInfo)
                }
            })
        }
    }

    fun sendMessageToChannel(channelSid: String, messageText: String, onMessageSent: (Message) -> Unit = {}): Observable<Message> {
        return Observable.create { emitter ->
            chatClient.channels.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    channel.messages.sendMessage(Message.options().withBody(messageText), object : CallbackListener<Message>() {
                        override fun onSuccess(message: Message) {
                            onMessageSent(message)
                            emitter.onNext(message)
                        }
                    })
                }
            })
        }
    }

    fun getChatMessages(numberOfLastMessages: Int): Observable<List<Message>> {
        return Observable.create { emitter ->

            chatClient.channels.getUserChannelsList(object : CallbackListener<Paginator<ChannelDescriptor>>() {
                override fun onSuccess(paginator: Paginator<ChannelDescriptor>) {
                    if (paginator.hasNextPage())
                        paginator.requestNextPage(object : CallbackListener<Paginator<ChannelDescriptor>>() {
                            override fun onSuccess(channelPaginator: Paginator<ChannelDescriptor>) {
                                channelPaginator.requestNextPage(object : CallbackListener<Paginator<ChannelDescriptor>>() {
                                    override fun onSuccess(channelPaginator: Paginator<ChannelDescriptor>) {
                                        channelPaginator.items.map {
                                            getMessageList(it, emitter, numberOfLastMessages)
                                        }
                                    }
                                })
                            }
                        })
                    else
                        paginator.items.map { getMessageList(it, emitter, numberOfLastMessages) }
                }

                override fun onError(errorInfo: ErrorInfo) {
                    super.onError(errorInfo)
                }
            })
        }
    }

    private fun getMessageList(it: ChannelDescriptor, emmitter: ObservableEmitter<List<Message>>, numberOfLastMessages: Int) {
        it.getChannel(object : CallbackListener<Channel>() {
            override fun onSuccess(channel: Channel) {
                channel.messages.getLastMessages(numberOfLastMessages, object : CallbackListener<List<Message>>() {
                    override fun onSuccess(list: List<Message>) {
                        emmitter.onNext(list)
                    }
                })
            }
        })
    }
}

