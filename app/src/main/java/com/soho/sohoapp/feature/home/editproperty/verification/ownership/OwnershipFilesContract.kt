package com.soho.sohoapp.feature.home.editproperty.verification.ownership

import android.net.Uri
import com.soho.sohoapp.data.listdata.Displayable
import com.soho.sohoapp.data.models.Attachment
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.feature.BaseViewInteractable

interface OwnershipFilesContract {
    interface ViewPresentable {
        fun onAddFileClicked()
        fun onDeleteIconClicked(attachment: Attachment)
        fun onPhotoPicked(uri: Uri)
        fun onTakeNewPhotoClicked()
        fun onChooseFromGalleryClicked()
        fun onPhotoReady(path: String)
        fun onSubmitClicked()
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun setFileList(fileList: List<Displayable>)
        fun getProperty(): Property
        fun pickImageFromGallery()
        fun capturePhoto()
        fun showAddPhotoDialog()
        fun showLoadingDialog()
        fun hideLoadingDialog()
    }
}