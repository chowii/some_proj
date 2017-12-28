package com.soho.sohoapp.preferences

import android.content.Context
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.data.models.User
import com.soho.sohoapp.utils.orFalse
import io.outbound.sdk.Outbound

class UserPrefs(context: Context) {
    private val SHARED_PREFS_AUTH_TOKEN: String = "SHARED_PREFS_AUTH_TOKEN"
    private val twilioTokenKey = this::class.java.`package`.name + ".twilioToken"
    private val twilioUserKey = this::class.java.`package`.name + ".twilioUser"
    private val prefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    var authToken: String
        private set(value) {
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

    fun logout() {
        DEPENDENCIES.userPrefs.authToken = ""
        DEPENDENCIES.userPrefs.user = null
        DEPENDENCIES.userPrefs.twilioToken = ""
        Outbound.logout()
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

}
