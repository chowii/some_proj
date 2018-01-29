package com.soho.sohoapp.feature.home.more.contract

import com.soho.sohoapp.data.models.User
import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 10/09/17.
 */
interface MoreContract {

    interface ViewPresentable {
        fun startPresenting()
        fun stopPresenting()
        fun getUser(forHelpActivity  : Boolean = false)
        fun addPropertyClicked()
        fun logout()
    }

    interface ViewInteractable {
        fun configureAdapter(model: List<BaseModel>)
        fun showSupportActivity(user: User)
        fun showUserProfileInfo(user: User)
    }
}