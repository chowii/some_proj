package com.soho.sohoapp.feature.home.editproperty.connections

import android.content.res.Resources
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.enums.PropertyUserRole
import com.soho.sohoapp.data.listdata.Displayable
import com.soho.sohoapp.data.models.ConnectionRequest
import com.soho.sohoapp.data.models.PropertyUser
import com.soho.sohoapp.feature.home.portfolio.data.Title
import com.soho.sohoapp.navigator.NavigatorInterface
import com.soho.sohoapp.navigator.RequestCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EditConnectionPresenter(private val view: EditConnectionContract.ViewInteractable,
                              private val navigator: NavigatorInterface,
                              private val resources: Resources) : AbsPresenter, EditConnectionContract.ViewPresentable {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val displayableList = mutableListOf<Displayable>()
    private var positionToDelete: Int? = null
    private var connectionToDelete: Displayable? = null

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        val currentRole = view.getPropertyFromExtras()?.propertyUsers?.find(
                { it.userDetails?.id == Dependencies.DEPENDENCIES.userPrefs.user?.id })?.role ?: PropertyUserRole.GUEST
        view.setUserCurrentRole(currentRole)
        val connections = view.getConnectionsFromExtras()
        displayableList.add(Title(resources.getString(R.string.connection_connected_parties)))
        connections?.apply {
            displayableList.addAll(users)
            displayableList.addAll(requests)
        }
        view.setData(displayableList)
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun onAddConnectionClicked() {
        navigator.openNewConnectionScreen(view.getPropertyFromExtras(), RequestCode.EDIT_PROPERTY_NEW_CONNECTION)
    }

    override fun onNewRequestCreated(request: ConnectionRequest) {
        displayableList.add(request)
        view.setData(displayableList)
    }

    override fun onConnectionSwipe(position: Int, connection: Displayable) {
        this.positionToDelete = position
        this.connectionToDelete = connection
        when (connection) {
            is PropertyUser -> view.showConfirmationDialog(connection.userDetails?.fullName())
            is ConnectionRequest -> view.showConfirmationDialog(connection.userDetails?.fullName())
        }
    }

    override fun onConfirmDeletionClicked() {
        when (connectionToDelete) {
            is PropertyUser -> (connectionToDelete as PropertyUser).id?.let { removeUserFromProperty(it) }
            is ConnectionRequest -> (connectionToDelete as ConnectionRequest).id?.let { revokeConnectionRequest(it) }
        }
    }

    private fun removeUserFromProperty(userId: Int) {
        view.showLoadingDialog()
        view.getPropertyFromExtras()?.id?.let {
            compositeDisposable.add(
                    DEPENDENCIES.sohoService.removeUserFromProperty(it, userId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    {
                                        view.hideLoadingDialog()
                                        positionToDelete?.let { view.removeItemFromList(it) }
                                    },
                                    {
                                        view.hideLoadingDialog()
                                        view.notifyDataSetChanged()
                                        view.showError(it)
                                    }))
        }
    }

    private fun revokeConnectionRequest(requestId: Int) {
        view.showLoadingDialog()
        view.getPropertyFromExtras()?.id?.let {
            compositeDisposable.add(
                    DEPENDENCIES.sohoService.revokeConnectionRequest(it, requestId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    {
                                        view.hideLoadingDialog()
                                        positionToDelete?.let { view.removeItemFromList(it) }
                                    },
                                    {
                                        view.hideLoadingDialog()
                                        view.notifyDataSetChanged()
                                        view.showError(it)
                                    }))
        }
    }
}