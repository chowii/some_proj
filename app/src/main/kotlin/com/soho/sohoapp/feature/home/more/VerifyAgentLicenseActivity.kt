package com.soho.sohoapp.feature.home.more

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.more.contract.VerifyAgentLicenseContract
import com.soho.sohoapp.feature.home.more.presenter.VerifyAgentLicensePresenter

/**
 * Created by chowii on 14/09/17.
 */
class VerifyAgentLicenseActivity: AppCompatActivity(), VerifyAgentLicenseContract.ViewInteractable {

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent = Intent(context, VerifyAgentLicenseActivity::class.java)
    }

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.header_license_number)
    lateinit var headerLicenseNumber: TextView

    @BindView(R.id.license_number_edit_text)
    lateinit var licenseNumberEditText: EditText

    @OnClick(R.id.save_agent_license_button)
    fun onSaveClick(){
        presenter.saveAgentLicenseNumber(licenseNumberEditText.text.toString())
    }

    lateinit var presenter: VerifyAgentLicensePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_agent_license)
        ButterKnife.bind(this)

        presenter = VerifyAgentLicensePresenter(this, this)
        presenter.startPresenting()
    }

    override fun configureView() {
        headerLicenseNumber.text = getString(R.string.license_number_string)
        configureToolbar()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.agent_license_toolbar_title)
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
