package com.soho.sohoapp.feature.landing.signup

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CheckBox
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.User
import com.soho.sohoapp.navigator.NavigatorImpl
import io.reactivex.disposables.Disposable

class RegisterUserInfoActivity : AppCompatActivity(), User.RegistrationCallback {

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, RegisterUserInfoActivity::class.java)
    }

    override fun onRegistrationSuccessful() = NavigatorImpl.newInstance(this).openHomeActivity()

    @BindView(R.id.first_name_edit_text)
    lateinit var nameEditText: EditText

    @BindView(R.id.last_name_edit_text)
    lateinit var lastNameEditText: EditText

    @BindView(R.id.country_edit_text)
    lateinit var countryEditText: EditText

    @BindView(R.id.buying_checkbox)
    lateinit var buyingCheckBox: CheckBox

    @BindView(R.id.selling_checkbox)
    lateinit var sellingCheckBox: CheckBox

    @BindView(R.id.renting_checkbox)
    lateinit var rentingCheckBox: CheckBox

    var registerDialog: ProgressDialog? = null

    var role: String = ""
    var user: User = User()

    @OnCheckedChanged(R.id.buying_checkbox, R.id.selling_checkbox, R.id.renting_checkbox)
    fun onCheckChanged() {
        if (buyingCheckBox.isChecked) role = "buying"
        else if (sellingCheckBox.isChecked) role = "selling"
        else if (rentingCheckBox.isChecked) role = "renting"
    }

    private var disposableUserProfile: Disposable? = null

    @OnClick(R.id.register_button)
    fun onRegisterClicked() {
        initProgressDialog()?.show()
        disposableUserProfile = user.updateUserProfile(hashMapOf(
                "first_name" to nameEditText.text.toString(),
                "last_name" to lastNameEditText.text.toString(),
                "country" to countryEditText.text.toString()
        ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user_info)
        ButterKnife.bind(this)
        user.registrationCallback = this
    }

    override fun onDestroy() {
        disposableUserProfile?.dispose()
        super.onDestroy()
    }
    private fun initProgressDialog(): ProgressDialog? {
        registerDialog?.setTitle("Registering")
        registerDialog?.setMessage("Please wait while we registerUser you")
        return registerDialog
    }

}
