package com.soho.sohoapp.feature.chat.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel
import com.twilio.chat.CallbackListener
import com.twilio.chat.Channel
import com.twilio.chat.Message
import io.reactivex.Observable

/**
 * Created by chowii on 19/12/17.
 */
class ChatChannel(private val chatChannel: Channel?) : BaseModel {

    override fun getItemViewType() = R.layout.item_chat_channel

    var property: ChatAttributes? = null
    var lastMessage: Message? = null

    var propertyId: Int? = null
    var propertyAddress: String? = null
    var users: MutableList<String>? = mutableListOf()


    init {
        property = Gson().fromJson<ChatAttributes>(chatChannel?.attributes.toString(), object : TypeToken<ChatAttributes>() {}.type)
        propertyId = property?.chatProperty?.id
        property?.chatProperty?.maskedAddress?.let {
            propertyAddress = if (it.isNotBlank())
                it
            else
                property?.chatProperty?.fullAddress
        }
        users = property?.chatConversation?.conversionUsers
        getLastMessageObservable()
    }

    fun getLastMessageObservable(): Observable<List<Message?>> {
        return Observable.create {
            chatChannel?.messages?.getLastMessages(1, object : CallbackListener<List<Message?>>() {
                override fun onSuccess(messageList: List<Message?>) {
                    lastMessage = messageList.firstOrNull()
                    it.onNext(messageList)
                }
            })
        }
    }

}