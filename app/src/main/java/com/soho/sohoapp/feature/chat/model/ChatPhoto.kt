package com.soho.sohoapp.feature.chat.model

import com.google.gson.annotations.SerializedName

/**
 * Created by chowii on 11/1/18.
 */
data class ChatPhoto(
        @SerializedName("url")
        val url: String
)