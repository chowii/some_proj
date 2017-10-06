package com.soho.sohoapp.feature.home.editproperty.verification.ownership

import com.soho.sohoapp.abs.AbsPresenter

class OwnershipDetailsPresenter(private val view: OwnershipDetailsContract.ViewInteractable)
    : AbsPresenter, OwnershipDetailsContract.ViewPresentable {

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
    }

    //not needed here
    override fun stopPresenting() {}

    override fun onAttachFilesClicked() {
        //todo: go to Files tab and open gallery
    }
}