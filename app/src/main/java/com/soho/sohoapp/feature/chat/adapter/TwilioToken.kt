package com.soho.sohoapp.feature.chat.adapter

import com.google.gson.annotations.SerializedName

/**
 * Created by chowii on 19/12/17.
 */
data class TwilioToken(
        @SerializedName("access_token")
        var accessToken: String,

        @SerializedName("user_identity")
        var userIdentity: String
)