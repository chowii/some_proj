package com.soho.sohoapp.feature.home.more.contract

import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 11/9/17.
 */
interface SettingsContract {

    interface ViewPresentable {
        fun startPresenting()
        fun stopPresenting()
        fun retrieveAccount()
    }

    interface ViewInteractable {
        fun configureToolbar()
        fun configureAdapter(list: List<BaseModel>)
    }

}