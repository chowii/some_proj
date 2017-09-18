package com.soho.sohoapp.feature.home.more.contract

/**
 * Created by chowii on 15/09/17.
 */
interface VerifyAgentLicenseContract {

    interface ViewPresentable {
        fun startPresenting()
        fun saveAgentLicenseNumber(toString: String)
        fun stopPresenting()
    }

    interface ViewInteractable {
        fun configureView()
    }

}