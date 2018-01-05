package com.soho.sohoapp.feature.chat.model

import com.google.gson.annotations.SerializedName

/**
 * Created by chowii on 22/12/17.
 */
class ChatConversation {

    @SerializedName("id")
    val id: Int? = null

    @SerializedName("conversation_users")
    val conversionUsers: ConversationUser? = null

    @SerializedName("channel_sid")
    val channelSid: String? = null

    @SerializedName("channel_unique_name")
    val channelUniqueName: String? = null

    @SerializedName("last_updated_at")
    val lastUpdatedAt: String? = null

}