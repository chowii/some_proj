package com.soho.sohoapp.feature.chat

import android.os.Build
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.feature.chat.model.TwilioToken
import com.soho.sohoapp.preferences.UserPrefs
import com.twilio.accessmanager.AccessManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 20/12/17.
 */
class ChatAccessManager(private val userPrefs: UserPrefs): AccessManager.Listener, AccessManager.TokenUpdateListener {

    private var twilioTokenDisposable: Observable<TwilioToken> = Dependencies.DEPENDENCIES.sohoService
            .getTwilioToken(Build.getRadioVersion())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private val compositeDisposable = CompositeDisposable()

    override fun onTokenExpired(accessManager: AccessManager) {
        updateToken(accessManager)
    }

    override fun onTokenWillExpire(accessManager: AccessManager) {
        updateToken(accessManager)
    }

    override fun onError(p0: AccessManager?, errorMessage: String?) {
        Dependencies.DEPENDENCIES.logger.d(errorMessage)
    }

    override fun onTokenUpdated(updatedToken: String) {
        Dependencies.DEPENDENCIES.userPrefs.twilioToken = updatedToken
    }

    private fun updateToken(accessManager: AccessManager) {
        compositeDisposable.add(
                twilioTokenDisposable.subscribe(
                        {
                            accessManager.updateToken(it.accessToken)
                            userPrefs.twilioToken = it.accessToken
                        },
                        { Dependencies.DEPENDENCIES.logger.e(it.message, it) }
                ))
    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }
}