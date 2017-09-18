package com.soho.sohoapp.feature.home.more.contract

import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 11/9/17.
 */
interface SettingsContract {

    interface ViewPresentable {
        fun startPresenting()
        fun retrieveAccount()
        fun stopPresenting()
        fun onSettingsItemClicked(item: String)
    }

    interface ViewInteractable {
        fun configureToolbar()
        fun updateAdapterDataset(dataset: List<BaseModel>)
    }

}