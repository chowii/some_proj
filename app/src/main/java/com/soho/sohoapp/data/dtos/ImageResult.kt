package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

class ImageResult {

    @SerializedName("url")
    var url: String? = null

    @SerializedName("small")
    var small: ImageResizedResult? = null

    @SerializedName("medium")
    var medium: ImageResizedResult? = null

    @SerializedName("large")
    var large: ImageResizedResult? = null
}