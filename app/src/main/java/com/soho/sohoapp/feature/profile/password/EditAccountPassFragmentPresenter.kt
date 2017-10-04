package com.soho.sohoapp.feature.profile.password

import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.network.SohoService
import com.soho.sohoapp.utils.QueryHashMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EditAccountPassFragmentPresenter(private val view: EditAccountPasswordFragContract.ViewInteractable
                                       , private val sohoService: SohoService
                                       , private val navigator: NavigatorImpl)
    : AbsPresenter, EditAccountPasswordFragContract.ViewPresentable {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun onChangePasswordButtonClick(values: QueryHashMap) {
        view.showLoading()
        compositeDisposable.add(sohoService.updatePassword(values)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.hideLoading()
                            view.resetFieldsAndFinish()
                            navigator.exitCurrentScreen()
                        },
                        {
                            view.hideLoading()
                            view.showError(it)
                        }))
    }
}