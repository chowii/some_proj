package com.soho.sohoapp.feature.chat.model

import com.google.gson.annotations.SerializedName

/**
 * Created by chowii on 19/12/17.
 */
data class ChatProperty(

        @SerializedName("id")
        val id: Int,

        @SerializedName("full_address")
        val fullAddress: String,

        @SerializedName("masked_address")
        val maskedAddress: String
)