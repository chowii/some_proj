package com.soho.sohoapp.feature.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.utils.checkEnableDisableAlpha
import com.soho.sohoapp.utils.isEmail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_forgot.*


class ForgotPasswordActivity : AbsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        initView()
    }

    private var disposable: Disposable? = null

    private fun initView() {
        reset_password_bt.checkEnableDisableAlpha(email_et.text.isEmail())

        email_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                reset_password_bt.checkEnableDisableAlpha(charSequence.isEmail())
            }
        })
        reset_password_bt.setOnClickListener {
            val loadingDialog = LoadingDialog(this)
            loadingDialog.show(getString(R.string.common_loading))
            val email = email_et.text.toString()

            val navigator = NavigatorImpl.newInstance(this)
            disposable = DEPENDENCIES.sohoService.sendForgotPasswordRequest(email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                loadingDialog.dismiss()
                                navigator.exitCurrentScreen()
                            }
                            ,
                            {
                                navigator.exitCurrentScreen()
                                loadingDialog.dismiss()
                                handleError(it)
                            })
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, ForgotPasswordActivity::class.java)
        }
    }
}
