package com.soho.sohoapp.feature.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API
import com.google.android.gms.auth.api.Auth.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.SohoApplication
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.data.dtos.UserResult
import com.soho.sohoapp.feature.landing.signup.SignUpActivity
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.utils.Converter
import com.soho.sohoapp.utils.orFalse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by chowii on 25/7/17.
 */
class LandingActivity : AbsActivity() {


    private val facebookManager by lazy { FacebookManager(this) }
    private val googleSigninOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.google_server_client_id))
                .build()
    }
    private val googleApiClient by lazy {
        GoogleApiClient.Builder(SohoApplication.getContext())
                .enableAutoManage(this /* FragmentActivity */
                        /* OnConnectionFailedListener */) {
                    DEPENDENCIES.logger.e("google api client connection failed")
                }
                .addApi(GOOGLE_SIGN_IN_API, googleSigninOptions)
                .build()
    }
    private val compositeDisposable by lazy { CompositeDisposable() }

    private val RC_GOOGLE_PLUS = 123

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
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_GOOGLE_PLUS) {
            loginWithGooglePlus(data)
        }
    }

    @OnClick(R.id.signup)
    fun onSignUpClicked() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    @OnClick(R.id.login)
    fun onLoginClicked() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    @OnClick(R.id.google_plus_login_btn)
    fun onGooglePlusLoginBtnClicked() {
        startActivityForResult(GoogleSignInApi.getSignInIntent(googleApiClient), RC_GOOGLE_PLUS)
    }

    @OnClick(R.id.fb_login_btn)
    fun onFbLoginBtnClicked() {
        compositeDisposable.clear()
        compositeDisposable.add(facebookManager.obs
                .observeOn(Schedulers.io()) //we are coming from the main thread
                .switchMap {
                    DEPENDENCIES.sohoService.loginWithFb(it.token)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            goHomeActivity(user)
                        },
                        { throwable ->
                            handleError(throwable)
                            DEPENDENCIES.logger.e("Error during  fb login", throwable)
                        }))
        facebookManager.login()
    }

    private fun loginWithGooglePlus(data: Intent?) {
        val result = GoogleSignInApi.getSignInResultFromIntent(data)
        if (result.isSuccess) {
            compositeDisposable.add(Observable.just(result.signInAccount?.idToken)
                    .switchMap {
                        DEPENDENCIES.sohoService.loginWithGoogle(it)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { user ->
                                goHomeActivity(user)
                            },
                            { throwable ->
                                handleError(throwable)
                                DEPENDENCIES.logger.e("Error during  google +login", throwable)
                            }))
        } else handleError()
    }

    private fun goHomeActivity(user: UserResult) {
        DEPENDENCIES.userPrefs.login(Converter.toUser(user))

        val navigator = NavigatorImpl.newInstance(this)
        if (!DEPENDENCIES.userPrefs.isProfileComplete.orFalse()) {
            navigator.showRegisterUserInfoActivity()
        } else {
            navigator.openHomeActivity()
        }
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