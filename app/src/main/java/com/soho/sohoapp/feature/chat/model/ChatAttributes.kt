package com.soho.sohoapp.feature.chat.model

import com.google.gson.annotations.SerializedName

/**
 * Created by chowii on 19/12/17.
 */
data class ChatAttributes(

        @SerializedName("property")
        val chatProperty: ChatProperty,

        @SerializedName("conversation")
        val chatConversation: LastMessageChatConversation

)