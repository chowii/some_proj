package com.soho.sohoapp.feature.chat.model

import android.support.annotation.LayoutRes
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.network.TwilioMessageTypeAdapterFactory
import com.twilio.chat.Message

/**
 * Created by chowii on 11/1/18.
 */
class ChatMessage(
        val message: Message,
        val chatConversation: ChatConversation?,
        @LayoutRes private val layoutRes: Int = R.layout.item_chat_conversation
) : BaseModel {

    var chatAttachment: TwilioMessageAttachment?

    init {
        chatAttachment =
                try {
                    GsonBuilder()
                            .registerTypeAdapterFactory(TwilioMessageTypeAdapterFactory())
                            .create()
                            .fromJson(
                                    message.attributes.toString(),
                                    object : TypeToken<TwilioMessageAttachment?>() {}.type
                            )
                } catch (e: Exception) {
                    e.message
                    null
                }
    }

    override fun getItemViewType() = layoutRes
}