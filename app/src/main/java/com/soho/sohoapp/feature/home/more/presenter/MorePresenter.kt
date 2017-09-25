package com.soho.sohoapp.feature.home.more.presenter

import android.content.Context
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.more.contract.MoreContract
import com.soho.sohoapp.feature.home.more.model.MoreItem
import com.soho.sohoapp.utils.Converter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 10/09/17.
 */
class MorePresenter(private val interactable: MoreContract.ViewInteractable, private val context: Context) : MoreContract.ViewPresentable {
    val compositeDisposable = CompositeDisposable()

    override fun getUser() {
        if (DEPENDENCIES.preferences.mUser == null) {
            compositeDisposable.add(DEPENDENCIES.sohoService.getProfile()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                val user = Converter.toUser(it)
                                DEPENDENCIES.preferences.mUser = user
                                interactable.showSupportActivity(user)
                            },
                            { throwable ->
                                DEPENDENCIES.logger.e(throwable.message)
                            }))
        } else {
            interactable.showSupportActivity(DEPENDENCIES.preferences.mUser)
        }
    }

    override fun startPresenting() {
        interactable.configureToolbar()
        interactable.configureAdapter(arrayListOf(
                MoreItem(getString(R.string.settings_item_text), R.drawable.settings)
                , MoreItem(getString(R.string.settings_help_item_text), R.drawable.settings)
                , MoreItem(getString(R.string.settings_log_out_item_text), R.drawable.settings)))

    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    fun getString(res: Int): String = context.getString(res)
}