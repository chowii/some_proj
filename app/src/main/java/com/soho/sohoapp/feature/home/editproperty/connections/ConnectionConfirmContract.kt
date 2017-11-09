package com.soho.sohoapp.feature.home.editproperty.connections

import android.support.annotation.StringRes
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyUser
import com.soho.sohoapp.feature.BaseViewInteractable

interface ConnectionConfirmContract {

    interface ViewPresentable {
        fun onBackClicked()
        fun onConfirmClicked()
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun getPropertyFromExtras(): Property?
        fun getUserFromExtras(): PropertyUser?
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showPropertyAddress(address: String?)
        fun showUserName(name: String?)
        fun showUserRole(@StringRes role: Int)
    }
}