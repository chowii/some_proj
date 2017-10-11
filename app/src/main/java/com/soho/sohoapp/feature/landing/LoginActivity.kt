package com.soho.sohoapp.feature.landing

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.network.Keys
import com.soho.sohoapp.utils.Converter
import com.soho.sohoapp.utils.checkEnableDisableAlpha
import com.soho.sohoapp.utils.orFalse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity() {

    @BindView(R.id.login_email_edit_text)
    lateinit var emailEditText: EditText

    @BindView(R.id.login_password_edit_text)
    lateinit var passwordEditText: EditText

    @BindView(R.id.login_button)
    lateinit var loginButton: Button

    var loadingDialog: LoadingDialog? = null

    @OnTextChanged(R.id.login_email_edit_text, R.id.login_password_edit_text)
    fun onTextChanged(editable: Editable) {
        loginButton.checkEnableDisableAlpha(
                (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()))
    }

    @OnClick(R.id.sign_up_redirect_button)
    fun onSignUpRedirectClicked(): Unit = NavigatorImpl.newInstance(this).openSignUpActivity()


    @OnClick(R.id.forgot_password_button)
    fun onForgotPasswordClicked(): Unit = NavigatorImpl.newInstance(this).openForgetPasswordActivity()

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        loadingDialog = LoadingDialog(this)
        loginButton.setOnClickListener {
            loadingDialog?.show(getString(R.string.login_loading))
            val map = hashMapOf(
                    Keys.User.EMAIL to emailEditText.text.toString(),
                    Keys.User.PASSWORD to passwordEditText.text.toString()
            )
            disposable = DEPENDENCIES.sohoService.loginUser(map)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user ->
                        DEPENDENCIES.prefs.user = Converter.toUser(user)
                        DEPENDENCIES.prefs.authToken = user.authenticationToken ?: ""
                        val navigatorImpl = NavigatorImpl.newInstance(this)
                        if (!DEPENDENCIES.prefs.isProfileComplete.orFalse()) {
                            navigatorImpl.showRegisterUserInfoActivity()
                        } else {
                            navigatorImpl.openHomeActivity()
                        }
                        loadingDialog?.dismiss()
                    },
                            { throwable ->
                                loadingDialog?.dismiss()
                                throwable.printStackTrace()
                                AlertDialog.Builder(this).setMessage(getString(R.string.error_occurred)).show()
                                DEPENDENCIES.logger.e("Error during login", throwable)
                            }
                    )
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
