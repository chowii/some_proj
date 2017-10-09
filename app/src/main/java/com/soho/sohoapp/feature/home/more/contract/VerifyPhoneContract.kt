package com.soho.sohoapp.feature.home.more.contract

import com.soho.sohoapp.feature.BaseViewInteractable

/**
 * Created by chowii on 13/9/17.
 */
interface VerifyPhoneContract {

    interface ViewPresentable {
        fun startPresenting()
        fun verifyPhoneNumber(phoneNumber: String)
        fun stopPresenting()
    }

    interface ViewInteractable : BaseViewInteractable {
        fun configureToolbar()
        fun showLoading()
        fun hideLoading()
    }
}