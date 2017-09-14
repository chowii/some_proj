package com.soho.sohoapp.feature.home.more

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.more.contract.VerifyPhoneContract
import com.soho.sohoapp.feature.home.more.presenter.VerifyPhonePresenter

/**
 * Created by chowii on 13/9/17.
 */
class VerifyPhoneActivity : AppCompatActivity(), VerifyPhoneContract.ViewInteractable {
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

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.phone_number_country_code)
    lateinit var phoneNumberCountryCode: Spinner

    @BindView(R.id.phone_number)
    lateinit var phoneNumberEditText: EditText

    @OnClick(R.id.verify_phone_button)
    fun onVerifyPhoneButtonClick(){
        val phoneNumber = countryCode.toString() + phoneNumberEditText.text.toString()
        presenter.verifyPhoneNumber(phoneNumber)
    }

    private lateinit var presenter: VerifyPhonePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone)
        ButterKnife.bind(this)
        presenter = VerifyPhonePresenter(this, this)

        val adapter = ArrayAdapter.createFromResource(this, R.array.country_code_options, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        phoneNumberCountryCode.adapter = adapter

        phoneNumberCountryCode.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, id: Long) {
                val countryCodeOptions = resources.getStringArray(R.array.country_code_options)
                countryCode = countryCodeOptions[pos].replace("+", "", false).toInt()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
        presenter.startPresenting()
    }

    override fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings_phone_toolbar_title)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stopPresenting()
    }

}