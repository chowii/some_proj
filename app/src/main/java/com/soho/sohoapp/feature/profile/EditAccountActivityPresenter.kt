package com.soho.sohoapp.feature.profile

import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.models.User
import io.reactivex.disposables.CompositeDisposable

class EditAccountActivityPresenter(private val view: EditAccountActivityContract.ViewInteractable
                                   , private val user: User?)
    : AbsPresenter, EditAccountActivityContract.ViewPresentable {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun startPresenting(fromConfigChanges: Boolean) {
        view.setPresentable(this)
        view.showUserFragment()
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }
}