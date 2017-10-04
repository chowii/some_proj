package com.soho.sohoapp.feature.profile.password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soho.sohoapp.Constants
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.feature.BaseViewInteractable
import com.soho.sohoapp.feature.landing.BaseFragment
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.network.Keys
import com.soho.sohoapp.utils.QueryHashMap
import kotlinx.android.synthetic.main.fragment_edit_account_password.*

/**
 * Created by mariolopez on 27/9/17.
 */
class EditAccountPasswordFragment : BaseFragment(), EditAccountPasswordFragContract.ViewInteractable {

    private lateinit var presentable: EditAccountPasswordFragContract.ViewPresentable
    private lateinit var presenter: EditAccountPassFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_account_password, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = EditAccountPassFragmentPresenter(this
                , DEPENDENCIES.sohoService
                , NavigatorImpl.newInstance(this))
        presenter.startPresenting(false)

        initView()
    }

    private fun initView() {
        new_pass_et.addTextChangedListener(rulesForPasswordWatcher)
        confirm_new_pass_et.addTextChangedListener(rulesForPasswordWatcher)
        change_password_bt.setOnClickListener {
            presenter.onChangePasswordButtonClick(QueryHashMap().apply {
                put(Keys.User.CURRENT_PASSWORD, current_pass_et.text.toString())
                put(Keys.User.PASSWORD, new_pass_et.text.toString())
                put(Keys.User.PASSWORD_CONFIRMATION, confirm_new_pass_et.text.toString())
            })
        }
        toggleButtonEnabled(0.4f, false)
    }

    private val rulesForPasswordWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            if (checkPasswordRequirements(charSequence)) {
                if (checkPasswordRequirements(new_pass_et.text) && checkPasswordRequirements(confirm_new_pass_et.text))
                    toggleButtonEnabled(1f, true)

            } else toggleButtonEnabled(0.4f, false)
        }
    }

    private fun checkPasswordRequirements(charSequence: CharSequence?): Boolean {
        return (charSequence !=null
                && !charSequence.isEmpty()
                && charSequence.length >= Constants.PASSWORD_MIN_LENGTH //never null as we check it before
                && charSequence.any { Character.isDigit(it) })
    }

    private fun toggleButtonEnabled(alpha: Float, isEnabled: Boolean) {
        change_password_bt.alpha = alpha
        change_password_bt.isEnabled = isEnabled
    }

    override fun resetFieldsAndFinish() {
        current_pass_et.text.clear()
        new_pass_et.text.clear()
        confirm_new_pass_et.text.clear()
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: EditAccountPasswordFragContract.ViewPresentable) {
        this.presentable = presentable
    }

    private lateinit var loadingDialog: LoadingDialog

    override fun showLoading() {
        loadingDialog = LoadingDialog(this.activity)
        loadingDialog.show(getString(R.string.common_loading))
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }
}

interface EditAccountPasswordFragContract {

    interface ViewPresentable {
        fun onChangePasswordButtonClick(values: QueryHashMap)
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun showLoading()
        fun hideLoading()
        fun resetFieldsAndFinish()
    }
}