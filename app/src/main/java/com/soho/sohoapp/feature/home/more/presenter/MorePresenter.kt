package com.soho.sohoapp.feature.home.more.presenter

import android.content.Context
import android.util.Log
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.SohoApplication
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.feature.chat.TwilioChatManager
import com.soho.sohoapp.feature.chat.model.DeviceToken
import com.soho.sohoapp.feature.home.more.contract.MoreContract
import com.soho.sohoapp.feature.home.more.model.MoreItem
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.network.SohoService
import com.soho.sohoapp.preferences.UserPrefs
import com.soho.sohoapp.utils.Converter
import com.soho.sohoapp.utils.letX
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by chowii on 10/09/17.
 */
class MorePresenter(
        private val interactable: MoreContract.ViewInteractable,
        private val context: Context? = SohoApplication.getContext(),
        private val absActivity: AbsActivity,
        private val navigator: NavigatorImpl,
        private val sohoService: SohoService,
        private val userPrefs: UserPrefs,
        private val twilioManager: TwilioChatManager

) : MoreContract.ViewPresentable {

    val compositeDisposable = CompositeDisposable()

    private val settingsMenuList = arrayListOf(
            MoreItem(getString(R.string.settings_item_text), R.drawable.ic_menu_settings)
            , MoreItem(getString(R.string.settings_help_item_text), R.drawable.ic_menu_help)
            , MoreItem(getString(R.string.settings_log_out_item_text), R.drawable.ic_menu_logout))

    override fun getUser(forHelpActivity: Boolean) {
        if (DEPENDENCIES.userPrefs.user == null) {
            compositeDisposable.add(DEPENDENCIES.sohoService.getProfile()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                val user = Converter.toUser(it)
                                DEPENDENCIES.userPrefs.user = user

                                if (forHelpActivity)
                                    user?.let { interactable.showSupportActivity(it) }
                                else
                                    user?.let { interactable.showUserProfileInfo(it) }

                                if (user == null) DEPENDENCIES.logger.e("error while parsing the user")

                            },
                            { throwable ->
                                DEPENDENCIES.logger.e(throwable.message, throwable)
                            }))
        } else {
            DEPENDENCIES.userPrefs.user?.let {
                if (forHelpActivity)
                    interactable.showSupportActivity(it)
                else
                    interactable.showUserProfileInfo(it)
            }
        }
    }

    override fun addPropertyClicked() {
        if (!absActivity.isUserSignedIn) {
            navigator.openLandingActivity()
        } else {
            navigator.openAddPropertyScreen()
        }
    }

    override fun startPresenting() {
        getUser(false)
        interactable.configureAdapter(settingsMenuList)
    }


    override fun logout() {
        sohoService.unRegisterDevice(userPrefs.deviceApiInfo.deviceToken.orEmpty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .switchMap { twilioManager.unregisterFcm(userPrefs.fcmPushNotificationToken) }
                .subscribe(
                        {
                            userPrefs.letX {
                                authToken = ""
                                user = null
                                twilioToken = ""
                                twilioUser = ""
                                deviceApiInfo = DeviceToken(-1, "", "")
                            }
                            twilioManager.shutdown()
                            navigator.openLandingActivity()
                        },
                        {
                            Log.d("LOG_TAG---", "${it.message}: ")
                            DEPENDENCIES.logger.e(it.message, it)
                        }
                )
    }

    override fun stopPresenting() {
        compositeDisposable.clear()
    }

    fun getString(res: Int): String = context?.getString(res).orEmpty()
}