package com.soho.sohoapp.feature.chat.chatconversation.contract

import android.net.Uri
import com.soho.sohoapp.feature.BaseViewInteractable
import com.soho.sohoapp.feature.chat.model.ChatMessage
import com.twilio.chat.Member

/**
 * Created by chowii on 22/12/17.
 */
interface ChatConversationContract {

    interface ViewPresenter {

        fun startPresenting()

        fun startedTyping()

        fun pickImageFromGallery()

        fun takeImageWithCamera()

        fun getChatConversation()

        fun uploadGalleryImageFromIntent(uri: Uri)

        fun cleanImageDisposable()

        fun stopPresenting()

    }

    interface ViewInteractable : BaseViewInteractable {

        fun showLoading()

        fun showAvatar(url: String?)

        fun configureAdapter(messageList: MutableList<ChatMessage>)

        fun appendMessage(message: ChatMessage)

        fun pickImage()

        fun captureImage()

        fun typingStarted(member: Member)

        fun typingEnded(member: Member)

        fun hideLoading()

    }

}