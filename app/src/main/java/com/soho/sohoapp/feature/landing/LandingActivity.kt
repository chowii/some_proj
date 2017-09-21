package com.soho.sohoapp.feature.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.feature.landing.signup.SignUpActivity
import com.soho.sohoapp.navigator.NavigatorImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by chowii on 25/7/17.
 */
class LandingActivity : AbsActivity() {


    private val facebookManager by lazy { FacebookManager(this) }
    private val compositeDisposable by lazy { CompositeDisposable() }

    /*** Facebook login handling end***/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        ButterKnife.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookManager.onActivityResult(requestCode, resultCode, data)
    }

    @OnClick(R.id.signup)
    fun onSignUpClicked() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    @OnClick(R.id.login)
    fun onLoginClicked() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    @OnClick(R.id.fb_login_btn)
    fun onFbLoginBtnClicked() {
        compositeDisposable.clear()
        compositeDisposable.add(facebookManager.obs
                .observeOn(Schedulers.io()) //we are coming from the main thread
                .switchMap {
                    DEPENDENCIES.sohoService.loginWithFb(it.token)
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            DEPENDENCIES.preferences.authToken = user.authenticationToken ?: ""
                            NavigatorImpl.newInstance(this).openHomeActivity()
                        },
                        { throwable ->
                            handleError(throwable)
                            DEPENDENCIES.logger.e("Error during  fb login", throwable)
                        }))
        facebookManager.login()
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context) = Intent(context, LandingActivity::class.java)

        @JvmStatic
        fun createIntent(context: Context, flags: Int): Intent {
            val intent = Intent(context, LandingActivity::class.java)
            intent.flags = flags
            return intent
        }
    }
}