package com.soho.sohoapp.feature.home.more

import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 10/09/17.
 */
interface MoreContract {

    interface ViewPresentable {
        fun startPresenting()
        fun stopPresenting()
    }

    interface ViewInteractable {
        fun configureToolbar()
        fun configureAdapter(model: List<BaseModel>)
    }
}