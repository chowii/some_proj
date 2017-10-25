package com.soho.sohoapp.feature.home.editproperty.files

import com.soho.sohoapp.data.listdata.Displayable
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyFile
import com.soho.sohoapp.feature.BaseViewInteractable

interface EditPropertyFilesContact {
    interface ViewPresentable {
        fun onAddFileClicked()
        fun onFileClicked(file: PropertyFile)
        fun onPropertyFileUpdated(propertyFile: PropertyFile, wasDeleted: Boolean)
        fun onPropertyFileCreated(propertyFile: PropertyFile)
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun getPropertyFromExtras(): Property
        fun setFileList(fileList: List<Displayable>)
    }
}