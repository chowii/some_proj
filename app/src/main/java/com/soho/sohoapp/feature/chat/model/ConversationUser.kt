package com.soho.sohoapp.feature.chat.model

import com.google.gson.annotations.SerializedName
import com.soho.sohoapp.data.models.BasicUser

/**
 * Created by chowii on 22/12/17.
 */
data class ConversationUser (

    @SerializedName("id")
    val id: Int,

    @SerializedName("conversation_id")
    val conversationId: Int,

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("member_sid")
    val memberSid: String,

    @SerializedName("last_message")
    val lastMessage: String,

    @SerializedName("last_message_sent_at")
    val lastMessageSentAt: Int,

    @SerializedName("user_twilio_id")
    val userTwilioId: String,

    @SerializedName("user")
    val user: BasicUser

)