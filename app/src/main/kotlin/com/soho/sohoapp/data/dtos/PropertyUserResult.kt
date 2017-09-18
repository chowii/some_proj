package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

/**
 * Created by Jovan on 15/9/17.
 */

class PropertyUserResult {

    @SerializedName("id")
    var id:Int? = null

    @SerializedName("role")
    var role:String? = null

    @SerializedName("user")
    var userDetails:BasicUserResult? = null

    @SerializedName("last_message")
    var lastMessage:String? = null

    @SerializedName("last_message_at")
    var lastMessageAt:String? = null

}