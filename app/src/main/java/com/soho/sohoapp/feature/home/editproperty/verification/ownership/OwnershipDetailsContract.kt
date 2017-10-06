package com.soho.sohoapp.feature.home.editproperty.verification.ownership

import com.soho.sohoapp.feature.BaseViewInteractable

interface OwnershipDetailsContract {

    interface ViewPresentable {
        fun onAttachFilesClicked()
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
    }
}