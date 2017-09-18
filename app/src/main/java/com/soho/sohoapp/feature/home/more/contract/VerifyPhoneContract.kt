package com.soho.sohoapp.feature.home.more.contract

/**
 * Created by chowii on 13/9/17.
 */
interface VerifyPhoneContract{

    interface ViewPresentable {
        fun startPresenting()
        fun verifyPhoneNumber(phoneNumber: String)
        fun stopPresenting()
    }

    interface ViewInteractable {
        fun configureToolbar()
    }

}