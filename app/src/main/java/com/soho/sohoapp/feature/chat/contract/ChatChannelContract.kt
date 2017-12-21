package com.soho.sohoapp.feature.chat.contract

import com.soho.sohoapp.feature.BaseViewInteractable
import com.soho.sohoapp.feature.chat.model.ChatChannel

/**
 * Created by chowii on 21/12/17.
 */
interface ChatChannelContract {

    interface ViewPresentable {

        fun startPresenting()

        fun getChatChannelList()

    }

    interface ViewInteractable : BaseViewInteractable {

        fun showLoading()

        fun onChannelSelected()

        fun updateChannelList(chat: ChatChannel)

        fun hideLoading()

    }

}