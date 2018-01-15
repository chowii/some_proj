package com.soho.sohoapp.feature.chat.chatconversation.contract

import android.net.Uri
import com.soho.sohoapp.feature.BaseViewInteractable
import com.soho.sohoapp.feature.chat.model.ChatMessage

/**
 * Created by chowii on 22/12/17.
 */
interface ChatConversationContract {

    interface ViewPresenter {

        fun startPresenting()

        fun pickImageFromGallery()

        fun takeImageWithCamera()

        fun getChatConversation()

        fun uploadGalleryImageFromIntent(uri: Uri)

        fun stopPresenting()

    }

    interface ViewInteractable : BaseViewInteractable {

        fun showLoading()

        fun configureAdapter(messageList: MutableList<ChatMessage>)

        fun pickImage()

        fun captureImage()

        fun hideLoading()

    }

}