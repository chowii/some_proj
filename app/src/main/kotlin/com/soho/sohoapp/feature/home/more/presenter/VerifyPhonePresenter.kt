package com.soho.sohoapp.feature.home.more.presenter

import android.app.Activity
import android.app.AlertDialog
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
class VerifyPhonePresenter(val interactable: VerifyPhoneContract.ViewInteractable, val context: Activity) : ViewPresentable {

    lateinit var compositeDisposable: CompositeDisposable

    override fun verifyPhoneNumber(phoneNumber: String) {
        //TODO find correct way to update value for dataset
        //TODO create map of the right types
        compositeDisposable.add(
                Dependencies.DEPENDENCIES.sohoService.verifyPhoneNumber(hashMapOf(Keys.More.TEXT to phoneNumber))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ _ ->
                            NavigatorImpl.newInstance(context).openSettingActivity()
                            context.finish()
                        },{
                            Dependencies.DEPENDENCIES.logger.e("Error", it)
                            AlertDialog.Builder(context).setMessage("Verification Failed. Try again later").show()
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