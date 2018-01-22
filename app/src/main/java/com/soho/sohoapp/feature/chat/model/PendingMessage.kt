package com.soho.sohoapp.feature.chat.model

import android.net.Uri
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 18/1/18.
 */
data class PendingMessage(

        var imageFile: Pair<Uri, String>,

        var uploadSuccessful: Boolean = true

) : BaseModel {
    override fun getItemViewType() = R.layout.item_pending_chat_image
}