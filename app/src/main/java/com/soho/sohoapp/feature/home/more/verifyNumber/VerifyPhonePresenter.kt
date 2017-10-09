package com.soho.sohoapp.feature.home.more.verifyNumber

import android.app.Activity
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.feature.home.more.contract.VerifyPhoneContract
import com.soho.sohoapp.feature.home.more.contract.VerifyPhoneContract.ViewPresentable
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.network.Keys
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 13/9/17.
 */
class VerifyPhonePresenter(private val interactable: VerifyPhoneContract.ViewInteractable
                           , val context: Activity
                           , val navigator: NavigatorImpl) : ViewPresentable {

    lateinit var compositeDisposable: CompositeDisposable

    override fun verifyPhoneNumber(phoneNumber: String) {
        interactable.showLoading()
        compositeDisposable.add(
                Dependencies.DEPENDENCIES.sohoService.verifyPhoneNumber(hashMapOf(Keys.More.TEXT to phoneNumber))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext { interactable.hideLoading() }
                        .doOnError { interactable.hideLoading()  }
                        .subscribe(
                                {
                                    navigator.startEnterPinActivity(phoneNumber)
                                    navigator.exitCurrentScreen()
                                },
                                {
                                    interactable.showError(it)
                                })
        )
    }

    override fun startPresenting() {
        interactable.configureToolbar()
        compositeDisposable = CompositeDisposable()
    }

    override fun stopPresenting() {
        compositeDisposable.dispose()
    }
}