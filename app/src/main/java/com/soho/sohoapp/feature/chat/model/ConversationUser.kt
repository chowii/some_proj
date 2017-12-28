package com.soho.sohoapp.feature.chat.model

import com.google.gson.annotations.SerializedName
import com.soho.sohoapp.data.models.BasicUser

/**
 * Created by chowii on 22/12/17.
 */
class ConversationUser {

    @SerializedName("id")
    val id: Int? = null

    @SerializedName("conversation_id")
    val conversationId: Int? = null

    @SerializedName("user_id")
    val userId: Int? = null

    @SerializedName("member_sid")
    val memberSid: String? = null

    @SerializedName("last_message")
    val lastMessage: Int? = null

    @SerializedName("last_message_sent_at")
    val lastMessageSentAt: Int? = null

    @SerializedName("user_twilio_id")
    val userTwilioId: Int? = null

    @SerializedName("user")
    val user: BasicUser? = null

}