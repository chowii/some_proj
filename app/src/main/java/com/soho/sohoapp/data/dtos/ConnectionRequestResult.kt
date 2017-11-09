package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

class ConnectionRequestResult {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("role")
    var role: String? = null

    @SerializedName("user")
    var userDetails: BasicUserResult? = null

    @SerializedName("state")
    var state: String? = null

    @SerializedName("property_id")
    var propertyId: Int? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null
}