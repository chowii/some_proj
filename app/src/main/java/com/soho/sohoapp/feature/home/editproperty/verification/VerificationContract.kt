package com.soho.sohoapp.feature.home.editproperty.verification

import android.net.Uri
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.feature.BaseViewInteractable

interface VerificationContract {

    interface ViewPresentable {
        fun onBackClicked()
        fun onPhotoIdClicked()
        fun onOwnershipProofClicked()
        fun onTakeNewPhotoClicked()
        fun onChooseFromGalleryClicked()
        fun onPhotoReady(path: String)
        fun onPhotoPicked(uri: Uri)
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun showPhotoVerificationStatus(@StringRes status: Int)
        fun showOwnershipVerificationStatus(@StringRes status: Int)
        fun showPhotoVerificationColor(@ColorRes color: Int)
        fun showOwnershipVerificationColor(@ColorRes color: Int)
        fun getPropertyFromExtras(): Property?
        fun showAddPhotoDialog()
        fun capturePhoto()
        fun pickImageFromGallery()
        fun showLoadingDialog()
        fun hideLoadingDialog()
    }
}