package com.soho.sohoapp.feature.chat.model

import com.google.gson.annotations.SerializedName

/**
 * Created by chowii on 23/1/18.
 */
data class DeviceToken(

        @SerializedName("id")
        var id: Int? = null,

        @SerializedName("device_type")
        var deviceType: String? = null,

        @SerializedName("device_token")
        var deviceToken: String? = null

)