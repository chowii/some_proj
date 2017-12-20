package com.soho.sohoapp.feature.chat

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.chat.adapter.ChatChannelAdapter
import com.soho.sohoapp.feature.chat.model.ChatChannel
import com.soho.sohoapp.utils.QueryHashMap
import com.twilio.chat.CallbackListener
import com.twilio.chat.ChatClient
import com.twilio.chat.ErrorInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rx.Observable

/**
 * Created by mariolopez on 16/10/17.
 */
class ChatChannelFragment : Fragment() {

    companion object {

        @JvmField
        val TAG: String = this::class.java.name

        @JvmStatic
        fun newInstance() = ChatChannelFragment()
    }

    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView
    @BindView(R.id.swipeRefresh) lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_coming_soon, container, false)
        ButterKnife.bind(this, view)

        toolbar.title = getString(R.string.message_title)

        val chatBuilder = ChatClient.Properties.Builder().createProperties()
        swipeRefreshLayout.isRefreshing = true
        val map = QueryHashMap()
        map.put("device_id", "test")
        Dependencies.DEPENDENCIES.sohoService.getTwilioToken(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { twilio ->
                            context?.let {
                                createChatClient(it, twilio.accessToken, chatBuilder)
                                Log.d("LOG_TAG---", "load: " + twilio.accessToken)

                                swipeRefreshLayout.setOnRefreshListener {
                                    createChatClient(it, twilio.accessToken, chatBuilder)
                                    Log.d("LOG_TAG---", "refresh: " + twilio.accessToken)
                                }
                            }

                        },
                        {
                            Log.d("LOG_TAG---", "error: " + it.message)
                            swipeRefreshLayout.isRefreshing = false
                        }
                )
        return view
    }


    lateinit var adapter: ChatChannelAdapter

    private fun createChatClient(it: Context, token: String, chatBuilder: ChatClient.Properties) {

        ChatClient.create(it, token, chatBuilder, object : CallbackListener<ChatClient>() {
            override fun onSuccess(client: ChatClient?) {

                Log.d("LOG_TAG---", "Twilio token: ")
                client?.let { client ->

                    adapter = ChatChannelAdapter(mutableListOf())
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(context)

                    client.channels.subscribedChannels?.let {

                        val chatChannel = it.map {
                            val s = ChatChannel(it)
                            s.getLastMessage()
                            s
                        }
                        chatChannel.sortedByDescending { it.lastMessage?.timeStampAsDate }

                        chatChannel.map { chat ->

                            chat.getLastMessageObservable()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            {
                                                chat.lastMessage = it.firstOrNull()
                                                adapter.appendToMessageList(chat)
                                                adapter.notifyDataSetChanged()
                                                swipeRefreshLayout.isRefreshing = false
                                            },
                                            {
                                                Log.d("LOG_TAG---", "message error: " + it.message)
                                                swipeRefreshLayout.isRefreshing = false

                                            })
                        }
                    }


                }
            }

            override fun onError(errorInfo: ErrorInfo?) {
                Log.d("LOG_TAG---", "Twilio error: " + errorInfo?.message)
                super.onError(errorInfo)
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    /*                    client.channels.subscribedChannels?.map {

                        }
                        val channelList = client.channels?.subscribedChannels?.filter { it.status != Channel.SynchronizationStatus.ALL }
                        val list = mutableListOf<ChatChannel?>()

                        channelList?.map { channel ->
                            Log.d("LOG_TAG---", "status: " + channel.status)

                            val chat = ChatChannel(channel)
                            chat.getLastMessageObservable()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            {
                                                chat.lastMessage = it
                                                list.add(chat)
                                                adapter.updateMessageList(list)
                                                adapter.notifyDataSetChanged()
                                                swipeRefreshLayout.isRefreshing = false
                                            },
                                            {
                                                swipeRefreshLayout.isRefreshing = false

                                            }
                                    )

                        }*/
    /*    private fun createChat() : Observable<ChatChannel> {
            return Observable.create {
                val callbackChatClient = object : CallbackListener<ChatChannel>() {
                    override fun onSuccess(p0: ChatChannel?) {
                        it.onNext(p0)
                    }
                }

            }
        }
    */
/*
                        ChatChannel(channel)
                        channel.messages.getLastMessages(1, Observable.just())
                        channel?.messages?.getLastMessages(1, object : CallbackListener<MutableList<Message?>>() {
                            override fun onSuccess(messageList: MutableList<Message?>) {

                                adapter.updateMessageList(messageList)
                                adapter.notifyDataSetChanged()
                                swipeRefreshLayout.isRefreshing = false
                            }
                        })
*/
    internal fun naiveObserveSensorChanged(sensorManager: SensorManager, sensor: Sensor, samplingPreiodUs: Int): Observable<SensorEvent> {
        return Observable.create { subscriber ->
            val sensorEventListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    subscriber.onNext(event)
                }

                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                    // ignored for this example
                }
            }
            sensorManager.registerListener(sensorEventListener, sensor, samplingPreiodUs)
        }
    }

}