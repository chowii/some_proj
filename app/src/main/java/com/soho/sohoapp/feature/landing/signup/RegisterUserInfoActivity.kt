package com.soho.sohoapp.feature.landing.signup

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import butterknife.*
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.data.models.INTENTION
import com.soho.sohoapp.data.models.ROLE
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.network.Keys
import com.soho.sohoapp.utils.Converter
import com.soho.sohoapp.utils.QueryHashMap
import com.soho.sohoapp.utils.checkEnableDisableAlpha
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register_user_info.*
import java.util.*


class RegisterUserInfoActivity : AbsActivity() {

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent = Intent(context, RegisterUserInfoActivity::class.java)
    }

    @BindView(R.id.first_name_edit_text)
    lateinit var nameEditText: EditText

    @BindView(R.id.last_name_edit_text)
    lateinit var lastNameEditText: EditText

    @BindView(R.id.country_spinner)
    lateinit var countrySpinner: Spinner

    @BindView(R.id.buying_checkbox)
    lateinit var buyingCheckBox: CheckBox

    @BindView(R.id.register_button)
    lateinit var registerButton: Button

    @BindView(R.id.selling_checkbox)
    lateinit var sellingCheckBox: CheckBox

    @BindView(R.id.renting_checkbox)
    lateinit var rentingCheckBox: CheckBox

    private var intentions = HashSet<String>()
    private var registerDialog: ProgressDialog? = null
    private var role: ROLE = ROLE.USER()
    private var country: String? = null

    private var firstNameEntered: Boolean = false
    private var lastNameEntered: Boolean = false
    private var intentionsChecked: Boolean = false
    private var conditionsMet: Boolean = false
        get() = firstNameEntered && lastNameEntered && intentionsChecked

    @OnCheckedChanged(R.id.buying_checkbox, R.id.selling_checkbox, R.id.renting_checkbox)
    fun onCheckChanged(button: CompoundButton, checked: Boolean) {
        when (button.id) {
            R.id.buying_checkbox -> syncListIntentions(INTENTION.BUYING().const, checked)
            R.id.selling_checkbox -> syncListIntentions(INTENTION.SELLING().const, checked)
            R.id.renting_checkbox -> syncListIntentions(INTENTION.RENTING().const, checked)
        }
    }

    private fun syncListIntentions(intentionConst: String, checked: Boolean) {
        if (checked) intentions.add(intentionConst) else intentions.remove(intentionConst)
        intentionsChecked = rentingCheckBox.isChecked || sellingCheckBox.isChecked || buyingCheckBox.isChecked
        registerButton.checkEnableDisableAlpha(conditionsMet)
    }

    private var disposableUserProfile: Disposable? = null

    @OnCheckedChanged(R.id.im_agent_switch)
    fun onAgentRoleChanged(checked: Boolean) {
        role = if (checked) ROLE.AGENT() else ROLE.USER()
    }

    @OnTextChanged(value = R.id.first_name_edit_text, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    fun afterFirstNameInput(editable: Editable?) {
        firstNameEntered = !editable.isNullOrEmpty()
        registerButton.checkEnableDisableAlpha(conditionsMet)
    }

    @OnTextChanged(value = R.id.last_name_edit_text, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    fun afterLastNameInput(editable: Editable?) {
        lastNameEntered = !editable.isNullOrEmpty()
        registerButton.checkEnableDisableAlpha(conditionsMet)
    }

    @OnClick(R.id.register_button)
    fun onRegisterClicked() {
        initProgressDialog()?.show()
        val values = QueryHashMap().apply {
            put(Keys.User.INTENDED_INTENTIONS, intentions)
            put(Keys.User.INTENDE_ROLE, role.const)
            put(Keys.User.COUNTRY, country)
            put(Keys.User.LAST_NAME, lastNameEditText.text.toString())
            put(Keys.User.FIRST_NAME, nameEditText.text.toString())
        }
        val loadingDialog = LoadingDialog(this)
        loadingDialog.show(getString(R.string.common_loading))
        disposableUserProfile = DEPENDENCIES.sohoService.updateUserProfile(values)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Converter::toUser)
                .subscribe(
                        { user ->
                            loadingDialog.dismiss()
                            DEPENDENCIES.userPrefs.login(user)
                            NavigatorImpl.newInstance(this).openHomeActivity()
                        },
                        {
                            handleError(it)
                            loadingDialog.dismiss()
                        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user_info)
        ButterKnife.bind(this)
        initView()
    }

    private fun initView() {
        val availableLocales = Locale.getAvailableLocales()
                .map { it.displayCountry }
                .filter { !it.isNullOrBlank() }
                .distinctBy { it }
                .sorted()
        val dataAdapter = ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, availableLocales)
        dataAdapter.setDropDownViewResource(R.layout.country_simple_spinner_dropdown_item)
        country_spinner.adapter = dataAdapter
        val localeCountry = Locale.getDefault().displayCountry
        country_spinner.setSelection(availableLocales.indexOf(localeCountry))
        country_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>, p1: View?, pos: Int, p3: Long) {
                (p0.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                country = availableLocales[pos]
            }
        }
        registerButton.checkEnableDisableAlpha(conditionsMet)
    }


    override fun onDestroy() {
        disposableUserProfile?.dispose()
        super.onDestroy()
    }

    private fun initProgressDialog(): ProgressDialog? {
        registerDialog?.setTitle(getString(R.string.registering))
        registerDialog?.setMessage(getString(R.string.please_wait))
        return registerDialog
    }

}

