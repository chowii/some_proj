package com.soho.sohoapp.feature.chat.model

import com.google.gson.annotations.SerializedName

/**
 * Created by chowii on 19/12/17.
 */
data class ChatConversation(

        @SerializedName("id")
        val id: Int,

        @SerializedName("conversation_users")
        val conversionUsers: MutableList<String>

)