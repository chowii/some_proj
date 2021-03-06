package com.soho.sohoapp.feature.chat.chatconversation.contract

import android.net.Uri
import com.soho.sohoapp.feature.BaseViewInteractable
import com.soho.sohoapp.feature.chat.model.ChatMessage
import com.soho.sohoapp.feature.home.BaseModel
import com.twilio.chat.Member
import com.twilio.chat.Message

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

        fun uploadGalleryImageFromIntent(uri: Uri, filename: String)

        fun getMessageBefore(message: Message)

        fun cleanImageDisposable()

        fun stopPresenting()

    }

    interface ViewInteractable : BaseViewInteractable {

        fun showLoading()

        fun showAvatar(url: String?)

        fun prependMessageList(messageList: MutableList<out BaseModel>)

        fun configureAdapter(messageList: MutableList<out BaseModel>)

        fun configureToolbarTitle(st: String)

        fun updateImageMessage(message: ChatMessage)

        fun appendMessage(message: ChatMessage)

        fun pickImage()

        fun captureImage()

        fun notifyUploadFailed(filename: Pair<Uri, String>)

        fun typingStarted(member: Member)

        fun typingEnded(member: Member)

        fun hideLoading()

    }

}