package com.soho.sohoapp.feature.home.editproperty.files

import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyFile
import com.soho.sohoapp.feature.BaseViewInteractable

interface EditPropertyPreviewFileContract {

    interface ViewPresentable {
        fun onBackClicked()
        fun onDeleteClicked()
        fun onEditClicked()
        fun onConfirmDeletionClicked()
        fun onPropertyFileUpdated(propertyFile: PropertyFile)
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun showDeleteOption()
        fun getPropertyFromExtras(): Property?
        fun getPropertyFileFromExtras(): PropertyFile?
        fun showFileImage(filePhoto: String?)
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showConfirmationDialog()
    }
}