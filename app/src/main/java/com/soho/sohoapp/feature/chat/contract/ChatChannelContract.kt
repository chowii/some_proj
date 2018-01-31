package com.soho.sohoapp.feature.chat.contract

import com.soho.sohoapp.feature.BaseViewInteractable
import com.soho.sohoapp.feature.chat.model.ChatChannel
import com.soho.sohoapp.feature.home.BaseModel
import com.twilio.chat.Message

/**
 * Created by chowii on 21/12/17.
 */
interface ChatChannelContract {

    interface ViewPresentable {

        fun startPresenting()

        fun getChatChannelList()

        fun stopPresenting()

    }

    interface ViewInteractable : BaseViewInteractable {

        fun showLoading()

        fun onChannelUpdated(updatedChannelList: MutableList<BaseModel>)

        fun onChannelDeleted(updatedChannelList: MutableList<BaseModel>)

        fun updateChannelList(baseList: BaseModel)

        fun updateChannelLastMessage(message: Message)

        fun prependChannel(s: ChatChannel)

        fun hideLoading()
    }

}