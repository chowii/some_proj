package com.soho.sohoapp.feature.home.editproperty.archive

import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.navigator.NavigatorInterface

class ArchiveConfirmationPresenter(private val view: ArchiveConfirmationContract.ViewInteractable,
                                   private val navigator: NavigatorInterface) : AbsPresenter, ArchiveConfirmationContract.ViewPresentable {

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        val address = view.getPropertyAddressFromExtras()
        address?.let { view.showPropertyAddress(it) }
    }

    override fun stopPresenting() {}

    override fun onBackClicked() {
        navigator.exitCurrentScreen()
    }

    override fun onConfirmClicked() {
        navigator.exitWithResultCodeOk()
    }
}