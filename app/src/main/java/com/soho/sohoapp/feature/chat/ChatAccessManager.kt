package com.soho.sohoapp.feature.chat

import android.util.Log
import com.soho.sohoapp.Dependencies
import com.soho.sohoapp.preferences.UserPrefs
import com.twilio.accessmanager.AccessManager

/**
 * Created by chowii on 20/12/17.
 */
class ChatAccessManager(private val userPrefs: UserPrefs): AccessManager.Listener, AccessManager.TokenUpdateListener {
    override fun onTokenExpired(accessManager: AccessManager) {
        accessManager.updateToken(accessManager.token)
        userPrefs.twilioToken = accessManager.token
        Log.e("LOG_TAG---", "onTokenExpired: ")
        //TODO: check if working correctly
    }

    override fun onTokenWillExpire(accessManager: AccessManager) {
        accessManager.updateToken(accessManager.token)
        userPrefs.twilioToken = accessManager.token
        Log.e("LOG_TAG---", "onTokenWillExpire: ")
        //TODO: check if working correctly
    }

    override fun onError(p0: AccessManager?, p1: String?) {
        //TODO: check error
        Log.e("LOG_TAG---", "onError: ")
    }

    override fun onTokenUpdated(updatedToken: String) {
        userPrefs.twilioToken = updatedToken
        Log.e("LOG_TAG---", "onTokenUpdated: ")
        //TODO: check if working correctly
    }
}