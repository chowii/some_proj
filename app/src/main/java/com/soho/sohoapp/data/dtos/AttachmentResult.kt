package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

class AttachmentResult {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("file")
    var file: FileResult? = null
}