package com.soho.sohoapp.feature.home.editproperty.connections

import com.soho.sohoapp.data.listdata.Displayable
import com.soho.sohoapp.data.models.ConnectionRequest
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyConnections
import com.soho.sohoapp.feature.BaseViewInteractable

interface EditConnectionContract {

    interface ViewPresentable {
        fun onAddConnectionClicked()
        fun onNewRequestCreated(request: ConnectionRequest)
        fun onConnectionSwipe(position: Int, connection: Displayable)
        fun onConfirmDeletionClicked()
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun getPropertyFromExtras(): Property?
        fun getConnectionsFromExtras(): PropertyConnections?
        fun setData(data: List<Displayable>)
        fun showConfirmationDialog(name: String?)
        fun removeItemFromList(position: Int)
        fun notifyDataSetChanged()
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun setUserCurrentRole(role: String)
    }
}