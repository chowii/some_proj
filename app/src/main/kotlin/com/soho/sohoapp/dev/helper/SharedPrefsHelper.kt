package com.soho.sohoapp.dev.helper

import android.content.SharedPreferences
import com.soho.sohoapp.dev.feature.User

/**
 * Created by chowii on 25/7/17.
 */
class SharedPrefsHelper {

    companion object {
        private val TAG: String = "SharedPrefsHelper"
        private val SHARED_PREFS_AUTH_TOKEN: String = "SHARED_PREFS_AUTH_TOKEN"
        private val INSTANCE : SharedPrefsHelper = SharedPrefsHelper()
        fun getInstance(): SharedPrefsHelper = INSTANCE

    }
    private var sharedPreference: SharedPreferences? = null

    var mUser: User? = null

    var authToken: String = sharedPreference?.getString(SHARED_PREFS_AUTH_TOKEN, "") ?: ""




}