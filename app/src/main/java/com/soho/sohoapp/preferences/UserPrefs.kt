package com.soho.sohoapp.preferences

import android.content.Context
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.data.models.User
import com.soho.sohoapp.feature.chat.model.DeviceToken
import com.soho.sohoapp.utils.orFalse
import io.outbound.sdk.Outbound

class UserPrefs(context: Context) {

    private val packageName = this::class.java.`package`.name

    private val SHARED_PREFS_AUTH_TOKEN: String = "SHARED_PREFS_AUTH_TOKEN"
    private val twilioTokenKey = packageName + ".twilioToken"
    private val twilioUserKey = packageName + ".twilioUser"
    private val pushNotificationKey = packageName + ".push_notification_token"
    private val deviceIdKey = packageName + ".device_id"
    private val deviceTokenKey = packageName + ".device_token"
    private val deviceType = "DeviceGcm"
    private val prefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val INVALID_ID: Int = -1

    var authToken: String
        internal set(value) {
            prefs.edit()?.putString(SHARED_PREFS_AUTH_TOKEN, value)?.apply()
            DEPENDENCIES.logger.d(prefs.getString(SHARED_PREFS_AUTH_TOKEN, ""))
        }
        get() {
            return prefs.getString(SHARED_PREFS_AUTH_TOKEN, "")
        }
    var user: User? = null

    var hasInstalled: Boolean
        set(value) {
            prefs.edit()?.putBoolean("FirstInstall", value)?.apply()
        }
        get() = prefs.getBoolean("FirstInstall", false)

    fun login(user: User?) {

        if (user == null) {
            DEPENDENCIES.logger.e("user null, can't login")
            return
        }

        DEPENDENCIES.userPrefs.user = user
        DEPENDENCIES.userPrefs.authToken = user.authenticationToken.orEmpty()

        val outboundUser = io.outbound.sdk.User.Builder()
                .setUserId(user.firstName + user.lastName + user.email)
                .setFirstName(user.firstName)
                .setLastName(user.lastName)
                .setEmail(user.email)
                .build()
        Outbound.identify(outboundUser)
    }

    val isUserLoggedIn: Boolean
        get() = user != null && authToken.isNotBlank()

    companion object {
        private val SHARED_PREFS_NAME = "Soho-userPrefs"
    }

    var isProfileComplete: Boolean = false
        get() = user?.isProfileComplete.orFalse()

    var twilioToken: String
        get() = prefs.getString(twilioTokenKey, "")
        set(value) {
            prefs.edit()?.putString(twilioTokenKey, value)?.apply()
        }

    var twilioUser: String
        get() = prefs.getString(twilioUserKey, "")
        set(value) {
            prefs.edit()?.putString(twilioUserKey, value)?.apply()
        }

    var fcmPushNotificationToken: String
        get() = prefs.getString(pushNotificationKey, "")
        set(value) {
            prefs.edit()?.putString(pushNotificationKey, value)?.apply()
        }

    private var deviceId: Int
        get() = prefs.getInt(deviceIdKey, INVALID_ID)
        set(value) {
            prefs.edit()?.putInt(deviceIdKey, value)?.apply()
        }

    private var deviceToken: String
        get() = prefs.getString(deviceTokenKey, "")
        set(value) {
            prefs.edit()?.putString(deviceTokenKey, value)?.apply()
        }

    var deviceApiInfo: DeviceToken
        get() = DeviceToken(
                id = deviceId,
                deviceType = deviceType,
                deviceToken = deviceToken
        )
        set(value) {
            value.apply {
                this@UserPrefs.deviceId = id ?: INVALID_ID
                this@UserPrefs.deviceToken = deviceToken.orEmpty()
            }

        }


}
