package com.soho.sohoapp.feature.home.editproperty.verification

import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.enums.VerificationType
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.navigator.NavigatorInterface

class VerificationPresenter(private val view: VerificationContract.ViewInteractable,
                            private val navigator: NavigatorInterface) : AbsPresenter, VerificationContract.ViewPresentable {

    var property: Property? = null

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        property = view.getPropertyFromExtras()

        val photoVerification = property?.verifications?.find {
            VerificationType.LICENCE == it.type
        }

        val ownershipVerification = property?.verifications?.find {
            VerificationType.PROPERTY == it.type
        }

        photoVerification?.let {
            view.showPhotoVerificationStatus(it.getDisplayableString())
            view.showPhotoVerificationColor(it.getColor())
        }

        ownershipVerification?.let {
            view.showOwnershipVerificationStatus(it.getDisplayableString())
            view.showOwnershipVerificationColor(it.getColor())
        }
    }

    override fun stopPresenting() {
        // not needed here
    }

    override fun onBackClicked() {
        navigator.exitCurrentScreen()
    }

    override fun onOwnershipProofClicked() {
        view.showToastMessage(R.string.coming_soon)
    }

    override fun onPhotoIdClicked() {
        view.showToastMessage(R.string.coming_soon)
    }
}