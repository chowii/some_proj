package com.soho.sohoapp.feature.chat

import android.content.Context
import android.util.Log
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.feature.chat.adapter.ChatChannelListenerAdapter
import com.soho.sohoapp.preferences.UserPrefs
import com.soho.sohoapp.utils.letX
import com.twilio.chat.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.json.JSONObject

/**
 * Created by chowii on 28/12/17.
 */
class TwilioChatManager {

    private val chatBehaviourSubject: BehaviorSubject<ChatClient> = BehaviorSubject.create()
    var chatObservable: Observable<ChatClient> = chatBehaviourSubject.toFlowable(BackpressureStrategy.ERROR).toObservable()

    private val channelBehaviourSubject: BehaviorSubject<Channel> = BehaviorSubject.create()
    private var channel: Channel? = null
    private var chatClient: ChatClient? = null

    fun initChatClient(context: Context, userPrefs: UserPrefs): TwilioChatManager {
        if (chatClient == null) {
            val chatBuilder = ChatClient.Properties.Builder().createProperties()
            ChatClient.create(context, userPrefs.twilioToken, chatBuilder, object : CallbackListener<ChatClient>() {
                override fun onSuccess(client: ChatClient) {
                    chatClient = client
                    chatBehaviourSubject.onNext(client)
                    registerPushNotification(userPrefs)
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

    private fun registerPushNotification(userPrefs: UserPrefs) {
        chatClient?.registerFCMToken(userPrefs.fcmPushNotificationToken, object : StatusListener() {
            override fun onSuccess() {
                Log.d("LOG_TAG---", "registered: ")
            }

            override fun onError(errorInfo: ErrorInfo) {
                Log.d("LOG_TAG---", "${errorInfo.message}: ")
                Dependencies.DEPENDENCIES.logger.e(errorInfo.message)
                super.onError(errorInfo)
            }
        })
    }

    fun initChannel(channelSid: String) {
        chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
            override fun onSuccess(channel: Channel) {
                this@TwilioChatManager.channel = channel
                channelBehaviourSubject.onNext(channel)
            }

            override fun onError(errorInfo: ErrorInfo?) {
                channelBehaviourSubject.onError(Throwable(errorInfo?.message))
                super.onError(errorInfo)
            }
        })
    }

    fun sendMessageToChannel(channelSid: String, messageText: String, onMessageSent: (Message) -> Unit = {}): Observable<Message> {
        return Observable.create { emitter ->
            chatClient?.channels?.getChannel(channelSid, object : CallbackListener<Channel>() {
                override fun onSuccess(channel: Channel) {
                    Log.d("LOG_TAG---", "onSuccessSyncState(): ${channel.synchronizationStatus}")
                    channel.addListener(object : ChatChannelListenerAdapter() {

                        override fun onSynchronizationChanged(channel: Channel) {
                            sendMessage(channel)
                        }
                    })
                    sendMessage(channel)
                }

                private fun sendMessage(channel: Channel) {
                    if (channel.synchronizationStatus == Channel.SynchronizationStatus.ALL)
                        channel.messages.sendMessage(
                                Message.options()
                                        .withBody(messageText)
                                        .withAttributes(JSONObject("{}")),
                                object : CallbackListener<Message>() {
                                    override fun onSuccess(message: Message) {
                                        onMessageSent(message)
                                        emitter.onNext(message)
                                    }
                                })
                }

                override fun onError(errorInfo: ErrorInfo) {
                    emitter.onError(Throwable(errorInfo.message))
                    super.onError(errorInfo)
                }
            })
        }
    }

    fun getChatMessages(
            context: Context,
            userPrefs: UserPrefs,
            conversationId: String,
            numberOfLastMessages: Int
    ): Observable<MutableList<Message>> {
        return Observable.create { emitter ->
            if (chatClient == null) {
                initChatClient(context, userPrefs).chatObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { getChatMessage(conversationId, numberOfLastMessages, emitter) },
                                { emitter.onError(it) }
                        )
            }
            getChatMessage(conversationId, numberOfLastMessages, emitter)
        }
    }

    private fun getChatMessage(conversationId: String, numberOfLastMessages: Int, emitter: ObservableEmitter<MutableList<Message>>) {
        chatClient?.channels?.getChannel(conversationId, object : CallbackListener<Channel>() {
            override fun onSuccess(channel: Channel) {
                channel.messages.getLastMessages(numberOfLastMessages, object : CallbackListener<MutableList<Message>>() {
                    override fun onSuccess(messageList: MutableList<Message>) {
                        emitter.onNext(messageList)
                    }

                    override fun onError(errorInfo: ErrorInfo) {
                        emitter.onError(Throwable(errorInfo.message))
                        super.onError(errorInfo)
                    }
                })
            }
        })
    }

    fun notifyTypeStarted() {
        channel?.typing()
    }

    fun getMessageBefore(message: Message, numberOfLastMessages: Int) = message.letX {
        Observable.create<MutableList<Message>> {

            messages.getMessagesBefore(messageIndex - 1, numberOfLastMessages, object : CallbackListener<MutableList<Message>>() {

                override fun onSuccess(mutableList: MutableList<Message>) = it.onNext(mutableList)

                override fun onError(errorInfo: ErrorInfo) {
                    it.onError(Throwable(errorInfo.message))
                    super.onError(errorInfo)
                }
            })
        }
    }

    fun unregisterFcm(pushNotificationToken: String) = Observable.create<Boolean> {

        chatClient?.unregisterFCMToken(pushNotificationToken, object : StatusListener() {
            override fun onSuccess() {
                it.onNext(true)
            }

            override fun onError(errorInfo: ErrorInfo) {
                it.onError(Throwable(errorInfo.message))
            }
        })

    }

    fun shutdown(): Observable<Boolean> {
        return Observable.create {
            chatClient?.shutdown()
            chatClient = null
            it.onNext(true)
        }
    }

}

