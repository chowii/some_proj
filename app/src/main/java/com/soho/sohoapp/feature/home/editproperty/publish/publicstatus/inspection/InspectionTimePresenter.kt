package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.inspection

import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.models.InspectionTime
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.navigator.NavigatorInterface
import com.soho.sohoapp.navigator.RequestCode

class InspectionTimePresenter(private val view: InspectionTimeContract.ViewInteractable,
                              private val navigator: NavigatorInterface) : AbsPresenter, InspectionTimeContract.ViewPresentable {

    private val inspectionTimes: MutableList<InspectionTime> = mutableListOf()
    private var property: Property? = null

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        property = view.getPropertyFromExtras()

        property?.propertyListing?.inspectionTimes?.let { inspectionTimes.addAll(it) }
        view.setInspectionList(inspectionTimes)
    }

    override fun stopPresenting() {}

    override fun onBackClicked() {
        navigator.exitCurrentScreen()
    }

    override fun onAddTimeClicked() {
        property?.let { navigator.openNewInspectionTimeScreen(null, it.id, RequestCode.NEW_INSPECTION_TIME) }
    }

    override fun onInspectionTimeClicked(inspectionTime: InspectionTime) {
        property?.let { navigator.openNewInspectionTimeScreen(inspectionTime, it.id, RequestCode.NEW_INSPECTION_TIME) }
    }

    override fun onInspectionTimesUpdated(inspectionTime: InspectionTime, inspectionTimeIsCreated: Boolean) {
        if (inspectionTimeIsCreated) {
            inspectionTimes.add(inspectionTime)
        } else {
            inspectionTimes.remove(inspectionTime)
        }

        view.setInspectionList(inspectionTimes)

        property?.propertyListing?.inspectionTimes = inspectionTimes
        view.setResult(property)
    }
}