package com.soho.sohoapp.feature.home.more.presenter

import android.app.Activity
import android.app.AlertDialog
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.feature.home.more.contract.VerifyAgentLicenseContract
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.network.Keys
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 15/09/17.
 */
class VerifyAgentLicensePresenter(
        private val interactable: VerifyAgentLicenseContract.ViewInteractable,
        private val viewContext: Activity)
    : VerifyAgentLicenseContract.ViewPresentable {

    lateinit var compositeDisposable: CompositeDisposable

    override fun startPresenting() {
        compositeDisposable = CompositeDisposable()
        interactable.configureView()
    }

    override fun saveAgentLicenseNumber(agentLicense: String) {
        compositeDisposable.add(
                Dependencies.DEPENDENCIES.sohoService.verifyAgentLicenseNumber(hashMapOf(Keys.More.TEXT to agentLicense))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ _ ->
                            NavigatorImpl.newInstance(viewContext).openSettingActivity()
                            viewContext.finish()
                        }, {
                            Dependencies.DEPENDENCIES.logger.e("Error", it)
                            AlertDialog.Builder(viewContext).setMessage("Verification Failed. Try again later").show()
                        })
        )
    }

    override fun stopPresenting() {
        compositeDisposable.dispose()
    }
}