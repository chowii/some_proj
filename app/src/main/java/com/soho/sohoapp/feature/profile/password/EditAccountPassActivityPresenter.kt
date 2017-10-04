package com.soho.sohoapp.feature.profile.password

import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.models.User
import io.reactivex.disposables.CompositeDisposable

class EditAccountPassActivityPresenter(private val view: EditAccountPasswordContract.ViewInteractable
                                       , private val user: User?)
    : AbsPresenter, EditAccountPasswordContract.ViewPresentable {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        view.showPasswordFormFragment()
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }
}