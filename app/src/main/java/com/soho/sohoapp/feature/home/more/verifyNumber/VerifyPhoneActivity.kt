package com.soho.sohoapp.feature.home.more.verifyNumber

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.feature.home.more.contract.VerifyPhoneContract
import com.soho.sohoapp.navigator.NavigatorImpl
import java.util.*

/**
 * Created by chowii on 13/9/17.
 */
class VerifyPhoneActivity : AbsActivity(), VerifyPhoneContract.ViewInteractable {

    companion object {

        @JvmStatic
        fun createIntent(context: Context) = Intent(context, VerifyPhoneActivity::class.java)

        @JvmStatic
        fun createIntent(context: Context, flags: Int): Intent {
            val intent = Intent(context, VerifyPhoneActivity::class.java)
            intent.flags = flags
            return intent
        }
    }

    var countryCode: Int = 61
    private lateinit var loadingDialog: LoadingDialog

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.phone_number_country_code)
    lateinit var callingCodesSpinner: Spinner

    @BindView(R.id.phone_number)
    lateinit var phoneNumberEditText: EditText

    @OnClick(R.id.verify_phone_button)
    fun onVerifyPhoneButtonClick() {
        val phoneNumber = countryCode.toString() + phoneNumberEditText.text.toString()
        presenter.verifyPhoneNumber(phoneNumber)
    }

    private lateinit var presenter: VerifyPhonePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone)
        ButterKnife.bind(this)

        presenter = VerifyPhonePresenter(this, this, NavigatorImpl.newInstance(this))

        initView()
        presenter.startPresenting()
    }

    override fun onDestroy() {
        presenter.stopPresenting()
        super.onDestroy()
    }

    private fun initView() {
        val phoneUtil = PhoneNumberUtil.getInstance()
        val callingCodesSet = phoneUtil.supportedCallingCodes.sorted()
        val localeCallingCode = phoneUtil.getCountryCodeForRegion(Locale.getDefault().country)

        val callingCodes = ArrayAdapter<String>(this,
                R.layout.calling_codes_item, callingCodesSet.map { "+" + it })
        callingCodes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        callingCodesSpinner.adapter = callingCodes
        callingCodesSpinner.setSelection(callingCodesSet.indexOf(localeCallingCode))

        callingCodesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, id: Long) {

                countryCode = callingCodesSet[pos]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    override fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings_phone_toolbar_title)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading() {
        loadingDialog = LoadingDialog(this)
        loadingDialog.show(getString(R.string.common_loading))
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }

    override fun showError(throwable: Throwable) {
        handleError()
    }
}