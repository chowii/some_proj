package com.soho.sohoapp.feature.landing.signup


import android.content.Context
import android.content.Intent
import android.net.Uri
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SignUpActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SignUpActivity::class.java)
    }

    @BindView(R.id.register_email_edit_text)
    lateinit var emailEditText: EditText

    @BindView(R.id.register_password_edit_text)
    lateinit var passwordEditText: EditText

    @BindView(R.id.register_button)
    lateinit var registerButton: Button

    @OnTextChanged(R.id.register_email_edit_text, R.id.register_password_edit_text)
    fun onTextChanged(editable: Editable) {
        if (emailEditText.text.toString().isEmpty() || passwordEditText.text.toString().isEmpty())
            toggleButtonEnabled(0.4f, !editable.toString().isEmpty())
        else toggleButtonEnabled(1f, !editable.toString().isEmpty())
    }

    @OnClick(R.id.terms_and_condition_bt)
    fun onTermsAndConditionClicked() {
        NavigatorImpl.newInstance(this)
                .openExternalUrl(Uri.parse(getString(R.string.url_terms_and_conditions)))
    }

    private fun toggleButtonEnabled(alpha: Float, isEnabled: Boolean) {
        registerButton.alpha = alpha
        registerButton.isEnabled = isEnabled
    }

    @OnClick(R.id.register_button)
    fun onRegisterClicked() {
        val registerMap: Map<String, String> = hashMapOf(
                Keys.User.EMAIL to emailEditText.text.toString(),
                Keys.User.PASSWORD to passwordEditText.text.toString()
        )
        loadingDialog?.show(getString(R.string.register_loading))
        registerCall(registerMap)
    }

    var loadingDialog: LoadingDialog? = null
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        ButterKnife.bind(this)
        loadingDialog = LoadingDialog(this)
        toggleButtonEnabled(0.4f, false)
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    private fun registerCall(registerMap: Map<String, String>) {
        disposable = DEPENDENCIES.sohoService.register(registerMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            DEPENDENCIES.logger.d(user.authenticationToken)
                            DEPENDENCIES.preferences.mUser = Converter.toUser(user)
                            DEPENDENCIES.preferences.authToken = user.authenticationToken ?: ""
                            loadingDialog?.dismiss()

                            NavigatorImpl.newInstance(this).showRegisterUserInfoActivity()
                        },
                        { throwable ->
                            DEPENDENCIES.logger.e("error", throwable)
                            loadingDialog?.dismiss()
                        }
                )
    }

}
