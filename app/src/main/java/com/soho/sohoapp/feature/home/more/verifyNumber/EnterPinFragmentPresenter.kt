package com.soho.sohoapp.feature.home.more.verifyNumber

import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.R
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.network.Keys
import com.soho.sohoapp.utils.QueryHashMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by mariolopez on 9/10/17.
 */
class EnterPinFragmentPresenter(private val view: EnterPinFragmentContract.ViewInteractable
                                , private val navigator: NavigatorImpl
                                , private val dependencies: Dependencies)
    : EnterPinFragmentContract.ViewPresentable {

    private val compositeDisposable = CompositeDisposable()

    override fun startPresenting(fromConfigChanges: Boolean) {}

    override fun checkPin(pin: String) {
        view.showLoading()
        compositeDisposable.add(dependencies.sohoService
                .verifyPin(QueryHashMap()
                        .apply { put(Keys.Verification.PIN_CODE, pin) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { view.hideLoading() }
                .doOnError { view.hideLoading() }
                .subscribe(
                        { navigator.exitCurrentScreen() }
                        ,
                        {
                            view.clearPin()
                            view.showError(it)
                        }))
    }

    override fun resendPin(phoneNumber: String) {
        view.showLoading()
        compositeDisposable.add(
                dependencies.sohoService.verifyPhoneNumber(hashMapOf(Keys.More.TEXT to phoneNumber))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext { view.hideLoading() }
                        .doOnError { view.hideLoading() }
                        .subscribe(
                                { view.showToast(R.string.common_pin_sent) }
                                ,
                                {
                                    view.showError(it)
                                })
        )
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }
}