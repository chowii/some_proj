package com.soho.sohoapp.feature.chat.contract

import com.facebook.internal.Mutable
import com.soho.sohoapp.feature.BaseViewInteractable
import com.soho.sohoapp.feature.home.BaseModel

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

        fun hideLoading()

    }

}