package com.soho.sohoapp.feature.chat.model

import com.google.gson.annotations.SerializedName

/**
 * Created by chowii on 19/12/17.
 */
open class LastMessageChatConversation {

        @SerializedName("id")
        val id: Int? = null

        @SerializedName("conversation_users")
        val conversionUsers: MutableList<String>? = null

}