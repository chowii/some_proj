package com.soho.sohoapp.feature.chat.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel
import com.twilio.chat.CallbackListener
import com.twilio.chat.Channel
import com.twilio.chat.ErrorInfo
import com.twilio.chat.Message
import io.reactivex.Observable

/**
 * Created by chowii on 19/12/17.
 */
class ChatChannel(private val chatChannel: Channel?) : BaseModel {

    override fun getItemViewType() = R.layout.item_chat_channel

    var property: ChatAttributes = Gson().fromJson<ChatAttributes>(chatChannel?.attributes.toString(), object : TypeToken<ChatAttributes>() {}.type)
    var messageList: List<Message> = listOf()
    var isUnconsumed: Boolean = false

    var propertyId: Int? = null
    var propertyAddress: String? = null
    var users: List<String> = mutableListOf()


    init {
        propertyId = property.chatProperty.id
        property.chatProperty.maskedAddress.let {
            propertyAddress = if (it.isNotBlank())
                it
            else
                property.chatProperty.fullAddress
        }
        users = property.chatConversation.conversionUsers
        getLastMessageObservable()
    }

    fun getLastMessageObservable(): Observable<List<Message>> {
        return Observable.create {
            chatChannel?.messages?.getLastMessages(1, object : CallbackListener<List<Message>>() {
                override fun onSuccess(messageList: List<Message>) = it.onNext(messageList)
            })
        }
    }


    fun setChannelAsConsumed() {
        chatChannel?.messages?.setAllMessagesConsumed()
    }

    fun getUnconsumedMessageCount(): Observable<Long> {
        return Observable.create {
            chatChannel?.getUnconsumedMessagesCount(object : CallbackListener<Long>() {
                override fun onSuccess(count: Long) {
                    it.onNext(count)
                }

                override fun onError(errorInfo: ErrorInfo?) {
                    it.onError(Throwable(errorInfo?.message))
                    super.onError(errorInfo)
                }
            })
        }
    }

}