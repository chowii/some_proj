package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

class VerificationResult {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("type")
    var type: String? = null

    @SerializedName("text")
    var text: String? = null

    @SerializedName("state")
    var state: String? = null

    @SerializedName("attachments")
    var attachment: List<AttachmentResult>? = null
}