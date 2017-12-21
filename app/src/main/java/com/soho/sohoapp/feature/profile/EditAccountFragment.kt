package com.soho.sohoapp.feature.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import com.marchinram.rxgallery.RxGallery
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.*
import com.soho.sohoapp.dialogs.LoadingDialog
import com.soho.sohoapp.extensions.toDateLongWithIso8601DateTimeFormat
import com.soho.sohoapp.extensions.toStringWithDisplayFormat
import com.soho.sohoapp.feature.home.editproperty.dialogs.AddPhotoDialog
import com.soho.sohoapp.feature.landing.BaseFragment
import com.soho.sohoapp.imageloader.ImageLoader
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.network.Keys
import com.soho.sohoapp.permission.PermissionManagerImpl
import com.soho.sohoapp.utils.FileHelper
import com.soho.sohoapp.utils.QueryHashMap
import kotlinx.android.synthetic.main.fragment_edit_account.*
import java.util.*


/**
 * Created by mariolopez on 27/9/17.
 */
class EditAccountFragment : BaseFragment(), EditAccountContract.ViewInteractable {

    private lateinit var presentable: EditAccountContract.ViewPresentable
    private lateinit var presenter: EditAccountFragmentPresenter
    private lateinit var user: User

    companion object {
        private val KEY_BUNDLE_USER = "User"

        fun newInstance(user: User?) = EditAccountFragment().apply {
            arguments = Bundle().apply { putParcelable(KEY_BUNDLE_USER, user) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            presenter = EditAccountFragmentPresenter(this, arguments?.getParcelable(KEY_BUNDLE_USER)
                    , NavigatorImpl.newInstance(this)
                    , RxGallery.gallery(it, false, RxGallery.MimeType.IMAGE)
                    , RxGallery.photoCapture(it)
                    , PermissionManagerImpl.newInstance(it)
                    , AddPhotoDialog(it)
                    , FileHelper.newInstance(it))
            presenter.startPresenting(false)
        }
        initView()

    }

    private var availableLocales: List<String> = Locale.getAvailableLocales()
            .map { it.displayCountry }
            .filter { !it.isNullOrBlank() }
            .distinctBy { it }
            .sorted()

    private fun initView() {
        edit_photo_tv.setOnClickListener {
            presenter?.onEditPhotoClick()
        }

        country_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                user.country = availableLocales[pos]
            }
        }
    }

    override fun showError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun setPresentable(presentable: EditAccountContract.ViewPresentable) {
        this.presentable = presentable
    }

    override fun fillUserInfo(user: User) {
        this.user = user

        name_et.setText(user.firstName)
        last_name_et.setText(user.lastName)
        email_et.setText(user.email)

        if (context != null && !user.avatar?.smallImageUrl.isNullOrEmpty()) {
            val imageLoader = DEPENDENCIES.imageLoader
            val params = ImageLoader.Params.create()
                    .url(user.avatar?.smallImageUrl!!)
                    .view(user_avatar_iv)
                    .placeHolder(R.drawable.ic_account_circle_gray)
            imageLoader.load(params)
        }

        val calendar = Calendar.getInstance()
        if (user.dateOfBirth == null) {
            dob_et.text = calendar.timeInMillis.toStringWithDisplayFormat()
        } else {
            dob_et.text = user.dateOfBirth.toStringWithDisplayFormat()
            calendar.timeInMillis = user.dateOfBirth!!
        }

        dob_et.setOnClickListener {
            activity?.fragmentManager?.let {
                presenter.showDatePickerDialog(it, calendar)
            }
        }
        renting_cb.isChecked = user.isRenting()
        renting_cb.setOnCheckedChangeListener(checkBoxIntention)
        selling_cb.isChecked = user.isSelling()
        selling_cb.setOnCheckedChangeListener(checkBoxIntention)
        buying_cb.isChecked = user.isBuying()
        buying_cb.setOnCheckedChangeListener(checkBoxIntention)
        im_agent_switch.isChecked = user.isAgent()
        im_agent_switch.setOnCheckedChangeListener { _, checked ->
            user.role = if (checked) ROLE.AGENT().const else ROLE.USER().const
        }

        update_btn.setOnClickListener(updateProfile)
        change_password_tv.setOnClickListener { presenter?.onChangePasswordClick() }

        val dataAdapter = ArrayAdapter<String>(this.context,
                android.R.layout.simple_spinner_item, availableLocales)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        country_spinner.adapter = dataAdapter

        country_spinner.setSelection(availableLocales.indexOf(user.country))
    }

    override fun showPickedDate(calendarPicked: Calendar) {
        user.dateOfBirth = calendarPicked.timeInMillis
        dob_et.text = calendarPicked.timeInMillis.toStringWithDisplayFormat()

    }

    private val updateProfile: (View) -> Unit = {
        val values = QueryHashMap().apply {
            put(Keys.User.INTENDED_INTENTIONS, user.intentions)
            put(Keys.User.INTENDE_ROLE, user.role)
            put(Keys.User.COUNTRY, country_spinner.selectedItem)
            put(Keys.User.DOB, user.dateOfBirth.toDateLongWithIso8601DateTimeFormat())
            put(Keys.User.LAST_NAME, last_name_et.text.toString())
            put(Keys.User.FIRST_NAME, name_et.text.toString())
        }
        presenter?.updaterUserProfile(values)
    }


    private val checkBoxIntention = { compoundButton: CompoundButton, checked: Boolean ->
        when (compoundButton.id) {
            R.id.buying_cb -> syncListIntentions(INTENTION.BUYING().const, checked)
            R.id.selling_cb -> syncListIntentions(INTENTION.SELLING().const, checked)
            R.id.renting_cb -> syncListIntentions(INTENTION.RENTING().const, checked)
            else -> Unit
        }
    }

    private fun syncListIntentions(intentionConst: String, checked: Boolean) {
        if (checked) {
            user.intentions.add(intentionConst)
        } else {
            user.intentions.remove(intentionConst)
        }
    }

    override fun showAvatar(picUri: Uri) {
        user_avatar_iv.setImageURI(picUri)
        presenter?.cleanCameraDisposable()
    }

    private lateinit var loadingDialog: LoadingDialog

    override fun showLoading() {
        loadingDialog = LoadingDialog(this.activity)
        loadingDialog.show(getString(R.string.common_loading))
        update_btn.isEnabled = false
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
        update_btn.isEnabled = true
    }
}