package com.soho.sohoapp.helper

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.soho.sohoapp.feature.User

/**
 * Created by chowii on 25/7/17.
 */
class SharedPrefsHelper {

    companion object {
        private val TAG: String = "SharedPrefsHelper"
        private val SHARED_PREFS_AUTH_TOKEN: String = "SHARED_PREFS_AUTH_TOKEN"
        private val INSTANCE : SharedPrefsHelper = SharedPrefsHelper()
        fun getInstance(): SharedPrefsHelper = INSTANCE

        private var sharedPreference: SharedPreferences? = null
        fun init(context: Context){
            sharedPreference =  PreferenceManager.getDefaultSharedPreferences(context)
        }
    }

    var mUser: User? = null

    var authToken: String?
        set(value) {
            sharedPreference?.edit()?.putString(SHARED_PREFS_AUTH_TOKEN, value)?.apply()
            Log.d("LOG_TAG---", sharedPreference?.getString(SHARED_PREFS_AUTH_TOKEN, ""))
        }get() {
        return sharedPreference?.getString(SHARED_PREFS_AUTH_TOKEN, "") ?: ""
    }

    var hasInstalled: Boolean
        set(value) {
            sharedPreference?.edit()?.putBoolean("FirstInstall", value)?.apply()
        }
        get() = sharedPreference?.getBoolean("FirstInstall", false) ?: false



}