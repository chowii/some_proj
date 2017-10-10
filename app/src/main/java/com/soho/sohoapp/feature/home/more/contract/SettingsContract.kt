package com.soho.sohoapp.feature.home.more.contract

import android.net.Uri
import com.soho.sohoapp.feature.BaseViewInteractable
import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 11/9/17.
 */
interface SettingsContract {

    interface ViewPresentable {
        fun onSettingsItemClicked(item: String)
        fun onTakeNewPhotoClicked()
        fun onChooseFromGalleryClicked()
        fun onPhotoReady(path: String)
        fun onPhotoPicked(uri: Uri)
        fun onBackClicked()
    }

    interface ViewInteractable : BaseViewInteractable {
        fun updateAdapterDataset(dataset: List<BaseModel>)
        fun showAddPhotoDialog()
        fun capturePhoto()
        fun pickImageFromGallery()
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showLoadingView()
        fun hideLoadingView()
    }

}