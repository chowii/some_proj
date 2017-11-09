package com.soho.sohoapp.feature.home.editproperty.connections

import android.support.annotation.StringRes
import com.soho.sohoapp.data.models.ConnectionRequest
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.feature.BaseViewInteractable

interface NewConnectionContract {

    interface ViewPresentable {
        fun onBackClicked()
        fun onSendInviteClicked()
        fun onConnectionRoleClicked()
        fun onRoleSelected(role: String)
        fun onNewRequestCreated(request: ConnectionRequest)
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun showToastMessage(@StringRes message: Int)
        fun getFirstName(): String
        fun getEmail(): String
        fun getPropertyFromExtras(): Property?
        fun showLoadingView()
        fun hideLoadingView()
        fun showRolesBottomSheet(roles: List<String>)
        fun showConnectionRole(role: String)
    }
}