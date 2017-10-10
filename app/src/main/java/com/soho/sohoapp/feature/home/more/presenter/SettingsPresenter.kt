package com.soho.sohoapp.feature.home.more.presenter

import android.content.Intent
import android.net.Uri
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.SohoApplication.getStringFromResource
import com.soho.sohoapp.abs.AbsPresenter
import com.soho.sohoapp.data.models.Attachment
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.more.contract.SettingsContract
import com.soho.sohoapp.feature.home.more.model.AccountVerification
import com.soho.sohoapp.feature.home.more.model.SettingItem
import com.soho.sohoapp.feature.home.more.model.VerificationItem
import com.soho.sohoapp.feature.marketplaceview.feature.filters.fitlermodel.HeaderItem
import com.soho.sohoapp.navigator.NavigatorInterface
import com.soho.sohoapp.navigator.RequestCode
import com.soho.sohoapp.permission.PermissionManagerInterface
import com.soho.sohoapp.utils.Converter
import com.soho.sohoapp.utils.FileHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 11/9/17.
 */

class SettingsPresenter(private val interactable: SettingsContract.ViewInteractable,
                        private val permissionManager: PermissionManagerInterface,
                        private val navigatorImpl: NavigatorInterface,
                        private val fileHelper: FileHelper) : AbsPresenter, SettingsContract.ViewPresentable {

    private val compositeDisposable = CompositeDisposable()
    private val settingsList: MutableList<BaseModel> = mutableListOf()
    private var permissionDisposable: Disposable? = null

    override fun startPresenting(fromConfigChanges: Boolean) = retrieveAccount()

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    override fun onSettingsItemClicked(item: String) {
        when (item) {
            getStringFromResource(R.string.settings_account_verification_photo_id_text) -> verifyPhotoId()
            getStringFromResource(R.string.settings_account_verification_mobile_number_text) -> verifyPhone()
            getStringFromResource(R.string.settings_account_verification_agent_license_text) -> verifyLicense()
            else -> navigatorImpl.openEditProfileScreen(RequestCode.NOT_USED)
        }
    }

    override fun onTakeNewPhotoClicked() {
        if (permissionManager.hasStoragePermission()) {
            interactable.capturePhoto()
        } else {
            permissionDisposable = permissionManager.requestStoragePermission(RequestCode.EDIT_PROPERTY_PRESENTER_STORAGE)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ permissionEvent ->
                        if (permissionEvent.isPermissionGranted) {
                            interactable.capturePhoto()
                        }
                        permissionDisposable?.dispose()
                    }, { it.printStackTrace() })
            permissionDisposable?.let { compositeDisposable.add(it) }
        }
    }

    override fun onChooseFromGalleryClicked() {
        interactable.pickImageFromGallery()
    }

    override fun onPhotoReady(path: String) {
        sendImageToServer(Attachment().apply { filePath = path })
    }

    override fun onPhotoPicked(uri: Uri) {
        sendImageToServer(Attachment().apply { this.uri = uri })
    }

    override fun onBackClicked() {
        navigatorImpl.exitCurrentScreen()
    }

    private fun verifyPhotoId() {
        interactable.showAddPhotoDialog()
    }

    private fun verifyPhone() {
        navigatorImpl.startVerifyPhoneActivity(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
    }

    private fun verifyLicense() {
        navigatorImpl.startAgentLicenseActivity(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
    }

    private fun retrieveAccount() {
        interactable.showLoadingView()
        compositeDisposable.add(DEPENDENCIES.sohoService.retrieveVerificationList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { accountList ->
                            with(settingsList) {
                                clear()
                                add(HeaderItem<String>(getStringFromResource(R.string.settings_edit_profile_text)))
                                add(createSettingItem())
                                add(HeaderItem<String>(
                                        getStringFromResource(R.string.settings_account_verification_text)))
                                accountList.forEach {
                                    add(VerificationItem(it.id, it.type, it.state.type()))
                                }
                            }
                            interactable.updateAdapterDataset(settingsList)
                            interactable.hideLoadingView()
                        },
                        {
                            interactable.showError(it)
                            interactable.hideLoadingView()
                        }))
    }

    private fun createSettingItem(): SettingItem {
        val user = DEPENDENCIES.prefs.user
        return SettingItem(user?.getFullnameShort(), user?.dateOfBirth ?: 0, R.drawable.drivers_card)
    }

    private fun sendImageToServer(attachment: Attachment) {
        interactable.showLoadingDialog()
        compositeDisposable.add(Converter.toPhotoVerificationRequestBody(fileHelper, attachment)
                .switchMap<AccountVerification>
                { requestBody ->
                    DEPENDENCIES.sohoService.verifyPhotoId(requestBody)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { verification ->
                            interactable.hideLoadingDialog()
                            settingsList.filterIsInstance<VerificationItem>()
                                    .find { it.verificationId == verification.id }
                                    ?.state = verification.state.type()
                            interactable.updateAdapterDataset(settingsList)
                        },
                        {
                            interactable.showError(it)
                            interactable.hideLoadingDialog()
                        }))
    }
}