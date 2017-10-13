package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.inspection

import android.support.annotation.StringRes
import com.soho.sohoapp.data.models.InspectionTime
import com.soho.sohoapp.feature.BaseViewInteractable

interface NewInspectionTimeContract {
    interface ViewPresentable {
        fun onBackClicked()
        fun onDateClicked()
        fun onStartTimeClicked()
        fun onEndTimeClicked()
        fun onSaveClicked()
        fun onDeleteTimeClicked()
        fun onConfirmDeletionClicked()
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun getInspectionTimeFromExtras(): InspectionTime?
        fun getPropertyIdFromExtras(): Int?
        fun showToastMessage(@StringRes stringId: Int)
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showInspectionDate(date: String)
        fun showInspectionStartTime(time: String)
        fun showInspectionEndTime(time: String)
        fun showDeleteAction()
        fun disable()
        fun showConfirmationDialog()
    }
}