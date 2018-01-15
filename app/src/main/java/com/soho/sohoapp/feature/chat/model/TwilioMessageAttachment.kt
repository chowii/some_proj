package com.soho.sohoapp.feature.chat.model

import android.support.annotation.LayoutRes
import com.google.gson.annotations.SerializedName
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel


/**
 * Created by chowii on 12/1/18.
 */
data class TwilioMessageAttachment(

        @SerializedName("type")
        var type: String = "",

        @SerializedName("file")
        var file: TwilioMessageFile? = null,

        @LayoutRes private val layoutRes: Int = R.layout.item_chat_image

) : BaseModel {
        override fun getItemViewType() = layoutRes
}

data class TwilioMessageFile(

        @SerializedName("name")
        var name: String,

        @SerializedName("id")
        var id: Int,

        @SerializedName("url")
        var url: String,

        @SerializedName("size")
        var size: Int,

        @SerializedName("width")
        var width: Float,

        @SerializedName("height")
        var height: Float,

        @SerializedName("original_filename")
        var originalFilename: Any
)
