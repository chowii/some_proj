package com.soho.sohoapp.feature.chat.model

import com.google.gson.annotations.SerializedName


/**
 * Created by chowii on 11/1/18.
 */
data class ChatAttachmentRequest (

        @SerializedName("id")
        var id: Int,

        @SerializedName("message_sid")
        var messageSid: String,

        @SerializedName("file")
        var file: ChatPhoto,

        @SerializedName("height")
        var height: Int,

        @SerializedName("width")
        var width: Int,

        @SerializedName("original_filename")
        var originalFilename: Any
)
