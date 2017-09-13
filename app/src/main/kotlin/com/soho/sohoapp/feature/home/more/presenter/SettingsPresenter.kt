package com.soho.sohoapp.feature.home.more.presenter

import android.content.Context
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.SharedUser
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.more.contract.SettingsContract
import com.soho.sohoapp.feature.home.more.model.SettingItem
import com.soho.sohoapp.feature.home.more.model.VerificationItem
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.HeaderItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 11/9/17.
 */

class SettingsPresenter(var interactable: SettingsContract.ViewInteractable, private val context: Context) : SettingsContract.ViewPresentable {

    override fun startPresenting() = interactable.configureToolbar()


    override fun retrieveAccount() {
        Dependencies.DEPENDENCIES.sohoService.retrieveVerificationList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ accountList ->
                    val settingsList: MutableList<BaseModel> = ArrayList()
                    settingsList.add(HeaderItem<String>(context.getString(R.string.settings_edit_profile_text)))
                    settingsList.add(createSettingItem())

                    settingsList.add(HeaderItem<String>(
                                context.getString(R.string.settings_account_verification_text)))
                    accountList.forEach {
                        settingsList.add(
                                VerificationItem(
                                        it.type,
                                        it.state.type()
                                )
                        )
                    }
                    interactable.configureAdapter(settingsList)
                }, { Dependencies.DEPENDENCIES.logger.e("Error", it) })
    }

    private fun createSettingItem(): SettingItem {
        val user = SharedUser.getInstance().user
        return SettingItem(user?.getFullnameShort(), user?.dateOfBirth, R.drawable.drivers_card)
    }

    override fun stopPresenting() { }
}