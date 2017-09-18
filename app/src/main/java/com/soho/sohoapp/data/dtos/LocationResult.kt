package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

class LocationResult {

    @SerializedName("address_1")
    var address_1: String? = null

    @SerializedName("address_2")
    var address_2: String? = null

    @SerializedName("suburb")
    var suburb: String? = null

    @SerializedName("state")
    var state: String? = null

    @SerializedName("postcode")
    var postcode: String? = null

    @SerializedName("country")
    var country: String? = null

    @SerializedName("latitude")
    var latitude: Double? = null

    @SerializedName("longitude")
    var longitude: Double? = null

    @SerializedName("surrounding_suburbs")
    var surroundingSuburbs: List<String>? = null

    @SerializedName("mask_address")
    var maskAddress: Boolean = true

    @SerializedName("full_address")
    var fullAddress: String? = null

}