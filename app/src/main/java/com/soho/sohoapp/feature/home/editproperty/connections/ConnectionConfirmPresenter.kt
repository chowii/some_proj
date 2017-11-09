package com.soho.sohoapp.feature.home.editproperty.connections

import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.models.Property
import com.soho.sohoapp.data.models.PropertyUser
import com.soho.sohoapp.navigator.NavigatorInterface
import com.soho.sohoapp.utils.Converter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ConnectionConfirmPresenter(private val view: ConnectionConfirmContract.ViewInteractable,
                                 private val navigator: NavigatorInterface) : AbsPresenter, ConnectionConfirmContract.ViewPresentable {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var property: Property
    private lateinit var user: PropertyUser

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        property = view.getPropertyFromExtras() ?: Property()
        user = view.getUserFromExtras() ?: PropertyUser()

        view.showPropertyAddress(property.getLocationSafe().addressLine1)

        view.showUserRole(user.getDisplayableRole())
        user.userDetails?.let {
            view.showUserName(it.firstName)
        }
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun onBackClicked() {
        navigator.exitCurrentScreen()
    }

    override fun onConfirmClicked() {
        view.showLoadingDialog()
        val userMap = Converter.toMap(user)
        compositeDisposable.add(
                DEPENDENCIES.sohoService.inviteUserToProperty(property.id, userMap)
                        .map { userResult -> Converter.toConnectionRequest(userResult) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { propertyUser ->
                                    view.hideLoadingDialog()
                                    navigator.exitWithResultCodeOk(propertyUser)
                                },
                                {
                                    view.hideLoadingDialog()
                                    view.showError(it)
                                }))
    }

}