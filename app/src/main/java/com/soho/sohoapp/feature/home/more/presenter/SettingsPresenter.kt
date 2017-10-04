package com.soho.sohoapp.feature.home.more.presenter

import android.app.Activity
import android.content.Intent
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.SohoApplication.getStringFromResource
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.more.SettingsActivity.Companion.CAMERA_INTENT_REQUEST_CODE
import com.soho.sohoapp.feature.home.more.contract.SettingsContract
import com.soho.sohoapp.feature.home.more.model.SettingItem
import com.soho.sohoapp.feature.home.more.model.VerificationItem
import com.soho.sohoapp.feature.marketplaceview.feature.filters.fitlermodel.HeaderItem
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.navigator.RequestCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 11/9/17.
 */

class SettingsPresenter(var interactable: SettingsContract.ViewInteractable, val viewContext: Activity) :
        SettingsContract.ViewPresentable {
    val compositeDisposable = CompositeDisposable()
    private val navigatorImpl = NavigatorImpl.newInstance(viewContext)
    override fun startPresenting() = interactable.configureToolbar()

    override fun retrieveAccount() {
        compositeDisposable.add(DEPENDENCIES.sohoService.retrieveVerificationList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ accountList ->
                    val settingsList: MutableList<BaseModel> = ArrayList()
                    settingsList.add(HeaderItem<String>(getStringFromResource(R.string.settings_edit_profile_text)))
                    settingsList.add(createSettingItem())
                    settingsList.add(HeaderItem<String>(
                            getStringFromResource(R.string.settings_account_verification_text)))
                    accountList.forEach {
                        settingsList.add(
                                VerificationItem(
                                        it.type,
                                        it.state.type()
                                )
                        )
                    }
                    interactable.updateAdapterDataset(settingsList)
                }, { DEPENDENCIES.logger.e("Error", it) }))
    }


    private fun createSettingItem(): SettingItem {
        val user = DEPENDENCIES.preferences.mUser
        return SettingItem(user?.getFullnameShort(), user?.dateOfBirth ?: 0, R.drawable.drivers_card)
    }

    override fun onSettingsItemClicked(item: String) {
        when (item) {
            getStringFromResource(R.string.settings_account_verification_photo_id_text) -> verifyPhotoId()
            getStringFromResource(R.string.settings_account_verification_mobile_number_text) -> verifyPhone()
            getStringFromResource(R.string.settings_account_verification_agent_license_text) -> verifyLicense()
            else -> navigatorImpl.openEditProfileScreen(RequestCode.NOT_USED)
        }
    }

    private fun verifyPhotoId() {
        navigatorImpl.startCameraIntentForResult(CAMERA_INTENT_REQUEST_CODE)
        DEPENDENCIES.logger.d("Start camera")
    }

    private fun verifyPhone() {
        navigatorImpl.startVerifyPhoneActivity(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
    }

    private fun verifyLicense() {
        navigatorImpl.startAgentLicenseActivity(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }
}