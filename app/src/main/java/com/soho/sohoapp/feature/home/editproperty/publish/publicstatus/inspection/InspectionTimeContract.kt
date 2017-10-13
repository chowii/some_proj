package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.inspection

import com.soho.sohoapp.data.models.InspectionTime
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.feature.BaseViewInteractable

interface InspectionTimeContract {
    interface ViewPresentable {
        fun onBackClicked()
        fun onAddTimeClicked()
        fun onInspectionTimeClicked(inspectionTime: InspectionTime)
        fun onInspectionTimesUpdated(inspectionTime: InspectionTime, inspectionTimeIsCreated: Boolean)
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun getPropertyFromExtras(): Property?
        fun setInspectionList(inspectionTimes: List<InspectionTime>)
        fun setResult(property: Property?)
    }
}