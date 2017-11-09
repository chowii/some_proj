package com.soho.sohoapp.feature.home.editproperty.connections

import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.models.ConnectionRequest
import com.soho.sohoapp.data.models.PropertyUser
import com.soho.sohoapp.data.models.User
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole
import com.soho.sohoapp.navigator.NavigatorInterface
import com.soho.sohoapp.navigator.RequestCode
import com.soho.sohoapp.utils.Converter
import com.soho.sohoapp.utils.isEmail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewConnectionPresenter(private val view: NewConnectionContract.ViewInteractable,
                             private val navigator: NavigatorInterface) : AbsPresenter, NewConnectionContract.ViewPresentable {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val propertyUser = PropertyUser()
    private val user = User()
    private val userRoles = mutableListOf<PropertyRole>()

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        view.showLoadingView()
        view.getPropertyFromExtras()?.id?.let { getPropertyRoles(it) }
    }

    private fun getPropertyRoles(propertyId: Int) {
        compositeDisposable.add(
                DEPENDENCIES.sohoService.getPropertyUserRoles(propertyId)
                        .map { userRoles -> Converter.toPropertyRoleList(userRoles) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { roles ->
                                    userRoles.addAll(roles)
                                    view.hideLoadingView()
                                },
                                {
                                    view.hideLoadingView()
                                    view.showError(it)
                                }))
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun onBackClicked() {
        navigator.exitCurrentScreen()
    }

    override fun onSendInviteClicked() {
        if (dataIsValid()) {
            user.firstName = view.getFirstName()
            user.email = view.getEmail()
            propertyUser.userDetails = user
            navigator.openConnectionConfirmScreen(view.getPropertyFromExtras(), propertyUser, RequestCode.NEW_CONNECTION_CONFIRM)
        }
    }

    override fun onConnectionRoleClicked() {
        view.showRolesBottomSheet(userRoles.map { it.label })
    }

    override fun onRoleSelected(role: String) {
        view.showConnectionRole(role)
        propertyUser.role = userRoles.first { fileType -> fileType.label == role }.key
    }

    override fun onNewRequestCreated(request: ConnectionRequest) {
        navigator.exitWithResultCodeOk(request)
    }

    private fun dataIsValid(): Boolean {
        var dataIsValid = true
        if (propertyUser.role == null) {
            view.showToastMessage(R.string.new_connection_role_not_selected)
            dataIsValid = false
        } else if (view.getFirstName().isEmpty()) {
            view.showToastMessage(R.string.new_connection_full_name_empty)
            dataIsValid = false
        } else if (view.getEmail().isEmpty()) {
            view.showToastMessage(R.string.new_connection_email_empty)
            dataIsValid = false
        } else if (!view.getEmail().isEmail()) {
            view.showToastMessage(R.string.new_connection_email_not_valid)
            dataIsValid = false
        }
        return dataIsValid
    }

}