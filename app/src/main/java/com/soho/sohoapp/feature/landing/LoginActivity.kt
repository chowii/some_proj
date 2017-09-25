package com.soho.sohoapp.feature.landing

import android.app.AlertDialog
import android.app.ProgressDialog
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
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.utils.Converter
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

    var registerDialog: ProgressDialog? = null

    @OnTextChanged(R.id.login_email_edit_text, R.id.login_password_edit_text)
    fun onTextChanged(editable: Editable) {
        if (emailEditText.text.toString().isEmpty() || passwordEditText.text.toString().isEmpty())
            toggleButtonEnabled(0.4f, !editable.toString().isEmpty())
        else toggleButtonEnabled(1f, !editable.toString().isEmpty())
    }

    @OnClick(R.id.sign_up_redirect_button)
    fun onSignUpRedirectClicked(): Unit = NavigatorImpl.newInstance(this).openSignUpActivity()


    @OnClick(R.id.forgot_password_button)
    fun onForgotPasswordClicked(): Unit = NavigatorImpl.newInstance(this).openForgetPasswordActivity()

    @OnClick(R.id.login_button)
    fun onLoginClicked() {
        initProgressDialog()?.show()
        //TODO wait for login api call

    }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        registerDialog = ProgressDialog(this)
        toggleButtonEnabled(0.4f, false)
        loginButton.setOnClickListener {
            initProgressDialog()?.show()
            val map = hashMapOf(
                    "email" to emailEditText.text.toString(),
                    "password" to passwordEditText.text.toString()
            )

            disposable = DEPENDENCIES.sohoService.loginUser(map)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user ->
                        DEPENDENCIES.preferences.mUser = Converter.toUser(user)
                        DEPENDENCIES.preferences.authToken = user.authenticationToken ?: ""
                        NavigatorImpl.newInstance(this).openHomeActivity()
                        registerDialog?.dismiss()
                    },
                            { throwable ->
                                registerDialog?.dismiss()
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

    private fun toggleButtonEnabled(alpha: Float, isEnabled: Boolean) {
        loginButton.alpha = alpha
        loginButton.isEnabled = isEnabled
    }

    private fun initProgressDialog(): ProgressDialog? {
        registerDialog?.setTitle("Logging in")
        registerDialog?.setMessage("Please wait while we log you in")
        return registerDialog
    }
}