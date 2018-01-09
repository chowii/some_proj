package com.soho.sohoapp.feature.chat.chatconversation.contract

import com.soho.sohoapp.feature.BaseViewInteractable
import com.twilio.chat.Message

/**
 * Created by chowii on 22/12/17.
 */
interface ChatConversationContract {

    interface ViewPresenter {

        fun startPresenting()

        fun getChatConversation()

        fun stopPresenting()

    }

    interface ViewInteractable : BaseViewInteractable {

        fun showLoading()

        fun configureAdapter(messageList: List<Message>)

        fun hideLoading()

    }

}