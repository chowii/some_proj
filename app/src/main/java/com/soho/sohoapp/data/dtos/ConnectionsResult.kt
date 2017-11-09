package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

class ConnectionsResult {
    @SerializedName("users")
    var users: List<PropertyUserResult> = mutableListOf()

    @SerializedName("requests")
    var requests: List<ConnectionRequestResult> = mutableListOf()
}