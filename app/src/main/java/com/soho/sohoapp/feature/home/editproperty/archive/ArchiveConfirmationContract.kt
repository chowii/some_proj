package com.soho.sohoapp.feature.home.editproperty.archive

import com.soho.sohoapp.feature.BaseViewInteractable

interface ArchiveConfirmationContract {
    interface ViewPresentable {
        fun onBackClicked()
        fun onConfirmClicked()
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun getPropertyAddressFromExtras(): String?
        fun showPropertyAddress(address: String)
    }
}