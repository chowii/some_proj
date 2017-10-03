package com.soho.sohoapp.feature.home.editproperty.verification

import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.feature.BaseViewInteractable

interface VerificationContract {

    interface ViewPresentable {
        fun onBackClicked()
        fun onPhotoIdClicked()
        fun onOwnershipProofClicked()
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun showToastMessage(@StringRes resId: Int)
        fun showPhotoVerificationStatus(@StringRes status: Int)
        fun showOwnershipVerificationStatus(@StringRes status: Int)
        fun showPhotoVerificationColor(@ColorRes color: Int)
        fun showOwnershipVerificationColor(@ColorRes color: Int)
        fun getPropertyFromExtras(): Property?
    }
}