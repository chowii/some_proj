package com.soho.sohoapp.feature.home.editproperty.files

import android.net.Uri
import android.support.annotation.StringRes
import com.soho.sohoapp.data.models.PropertyFile
import com.soho.sohoapp.data.models.SohoOption
import com.soho.sohoapp.feature.BaseViewInteractable

interface EditPropertyAddFileContract {

    interface ViewPresentable {

        fun startPresenting()

        fun stopPresenting()

        fun getFileTypesList(): List<SohoOption>

        fun getCurrentPropertyFile(): PropertyFile

        fun onSaveClicked()

        fun onBackClicked()

        fun onAddFileClicked()

    }

    interface ViewInteractable : BaseViewInteractable {

        fun setViewPresentable(presentable: ViewPresentable)

        fun setupForm(currentFile: PropertyFile, fileTypes: List<SohoOption>)

        fun toggleRetrievingFileTypes(retrieving: Boolean)

        fun toggleSendingFileIndicator(sending: Boolean)

        fun fileSelected(uri: Uri)

        fun showToastMessage(@StringRes message: Int)

    }
}