package com.soho.sohoapp.feature.home.more.verifyNumber

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.content.res.ResourcesCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.feature.BaseViewInteractable
import com.soho.sohoapp.feature.landing.BaseFragment
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.utils.checkEnableDisableAlpha
import kotlinx.android.synthetic.main.fragment_enter_pin.*

/**
 * Created by mariolopez on 9/10/17.
 */
class EnterPinFragment : BaseFragment(), EnterPinFragmentContract.ViewInteractable {

    private lateinit var presentable: EnterPinFragmentContract.ViewPresentable
    private lateinit var presenter: EnterPinFragmentPresenter
    private lateinit var loadingDialog: LoadingDialog
    private val PIN_DIGITS: Int = 4

    companion object {
        fun newInstance(phoneNumber: String) = EnterPinFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(EnterPinActivity.Companion.EXTRA_PHONE_NUMBER, phoneNumber)
                    }
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_enter_pin, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = EnterPinFragmentPresenter(this, NavigatorImpl.newInstance(this)
                , Dependencies.DEPENDENCIES)

        initView()
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    private fun initView() {
        pin_view.apply {
            setAnimationEnable(true)
            //this works just programmatically
            setBorderColor(ResourcesCompat.getColor(resources, R.color.primary, null))
        }
        pin_view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(pin: CharSequence, p1: Int, p2: Int, p3: Int) {
                enter_pin_btn.checkEnableDisableAlpha(pin.length >= PIN_DIGITS)
            }
        })

        resend_confirmation_tv.setOnClickListener {
            presenter.resendPin(arguments.getString(EnterPinActivity.EXTRA_PHONE_NUMBER))
        }

        enter_pin_btn.setOnClickListener { presenter.checkPin(pin_view.text.toString()) }
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: EnterPinFragmentContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun showLoading() {
        loadingDialog = LoadingDialog(this.activity)
        loadingDialog.show(getString(R.string.common_loading))
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }

    override fun clearPin() {
        pin_view.text.clear()
    }

    override fun showToast(stringRes: Int) {
        Toast.makeText(this.activity,stringRes,Toast.LENGTH_SHORT).show()
    }
}

interface EnterPinFragmentContract {

    interface ViewPresentable : AbsPresenter {
        fun checkPin(pin: String)
        fun resendPin(phoneNumber: String)
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun showLoading()
        fun hideLoading()
        fun clearPin()
        fun showToast(@StringRes stringRes: Int)
    }
}