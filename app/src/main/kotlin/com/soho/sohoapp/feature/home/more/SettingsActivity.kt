package com.soho.sohoapp.feature.home.more

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.more.adapter.SettingsAdapter
import com.soho.sohoapp.feature.home.more.contract.SettingsContract
import com.soho.sohoapp.feature.home.more.presenter.SettingsPresenter

/**
 * Created by chowii on 10/09/17.
 */
class SettingsActivity: AppCompatActivity(), SettingsContract.ViewInteractable, SettingsAdapter.OnSettingsItemClickListener {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        ButterKnife.bind(this)

        presenter = SettingsPresenter(this, this)
        presenter.startPresenting()
        presenter.retrieveAccount()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stopPresenting()
    }

    override fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"
    }

    override fun configureAdapter(list: List<BaseModel>) {
        recyclerView.adapter = SettingsAdapter(list, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onSettingsItemClicked(item: String) {
        when (item) {
            getString(R.string.settings_account_verification_photo_id_text) -> openCamera()
            getString(R.string.settings_account_verification_mobile_number_text) -> verifyPhone()
            getString(R.string.settings_account_verification_agent_license_text) -> verifyLicense()
            else -> doElse()
        }
    }

    private fun doElse() {
        Toast.makeText(this, "Do Else", Toast.LENGTH_SHORT).show()
    }

    private fun verifyLicense() {
        Toast.makeText(this, "Agent License", Toast.LENGTH_SHORT).show()
    }

    private fun openCamera() {
        Toast.makeText(this, "Open Camera", Toast.LENGTH_SHORT).show()
    }

    private fun verifyPhone() {
        Toast.makeText(this, "Verify Phone", Toast.LENGTH_SHORT).show()
    }
}