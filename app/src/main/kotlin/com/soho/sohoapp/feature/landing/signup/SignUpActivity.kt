package com.soho.sohoapp.feature.landing.signup


import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.soho.sohoapp.R
import com.soho.sohoapp.helper.NavHelper
import com.soho.sohoapp.helper.SharedPrefsHelper
import com.soho.sohoapp.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignUpActivity : AppCompatActivity() {

    @BindView(R.id.register_email_edit_text)
    lateinit var emailEditText: EditText

    @BindView(R.id.register_password_edit_text)
    lateinit var passwordEditText: EditText

    @BindView(R.id.register_button)
    lateinit var registerButton: Button

    @OnTextChanged(R.id.register_email_edit_text, R.id.register_password_edit_text)
    fun onTextChanged(editable: Editable){
        if(emailEditText.text.toString().isEmpty() || passwordEditText.text.toString().isEmpty())
            toggleButtonEnabled(0.4f, !editable.toString().isEmpty())
        else toggleButtonEnabled(1f, !editable.toString().isEmpty())
    }

    var registerDialog: ProgressDialog? = null

    private fun toggleButtonEnabled(alpha: Float, isEnabled: Boolean) {
        registerButton.alpha = alpha
        registerButton.isEnabled = isEnabled
    }


    @OnClick(R.id.register_button)
    fun onRegisterClicked(){
        val registerMap: Map<String, String> = hashMapOf(
                "email" to emailEditText.text.toString(),
                "password" to passwordEditText.text.toString()
        )
        registerCall(registerMap)
        initProgressDialog()?.show()
    }

    private fun initProgressDialog(): ProgressDialog? {
        registerDialog?.setTitle("Registering")
        registerDialog?.setMessage("Please wait while we register you")
        return registerDialog
    }

    private fun registerCall(registerMap: Map<String, String>) {
        synchronized(this){

        ApiClient.getService().register(registerMap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            Log.v("LOG_TAG---", user.authenticationToken)
                            SharedPrefsHelper.getInstance().mUser = user
                            SharedPrefsHelper.getInstance().authToken = user.authenticationToken!!
                            initProgressDialog()?.dismiss()
                            NavHelper.showRegisterUserInfoActivity(this)
                        },
                        { error ->
                            Log.v("LOG_TAG---", "error")
                            initProgressDialog()?.dismiss()
                        }
                )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        ButterKnife.bind(this)
        registerDialog = ProgressDialog(this)
        toggleButtonEnabled(0.4f, false)
    }
}
