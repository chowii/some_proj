package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Jovan on 9/10/17.
 */

class PropertyFileResult {

    @SerializedName("id")
    var id:Int? = null

    @SerializedName("document_name")
    var name:String? = null

    @SerializedName("attachment")
    var fileAttachment:FileResult? = null

    @SerializedName("document_type")
    var documentType:String? = null

    @SerializedName("is_cost")
    var isCost:Boolean? = null

    @SerializedName("amount")
    var amount:Float? = null

    @SerializedName("updated_at")
    var updatedAt: Date? = null

}