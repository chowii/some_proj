package com.soho.sohoapp.feature.landing

import android.app.Activity
import android.content.Intent
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.logger.Logger
import io.reactivex.Observable
import java.util.*

class FacebookManager(private val activity: Activity) {
    private val logger: Logger = DEPENDENCIES.logger
    private val callbackManager: CallbackManager = CallbackManager.Factory.create()
    private val loginManager = LoginManager.getInstance()!!

    val obs = Observable.create<AccessToken> { emitter ->
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                logger.d("Facebook sign up is successful")
                emitter.onNext(loginResult.accessToken)
            }

            override fun onCancel() {
                val errorMessage = "Facebook sign up is canceled"
                logger.d(errorMessage)
                emitter.onError(FacebookException(errorMessage))
            }

            override fun onError(exception: FacebookException) {
                logger.w("Error during Facebook sign up", exception)
                emitter.onError(exception)
            }
        })
    }

    fun login() {
        loginManager.logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"))
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}
