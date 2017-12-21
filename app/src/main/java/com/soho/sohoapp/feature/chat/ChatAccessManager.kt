package com.soho.sohoapp.feature.chat

import android.util.Log
import com.soho.sohoapp.Dependencies
import com.twilio.accessmanager.AccessManager

/**
 * Created by chowii on 20/12/17.
 */
class ChatAccessManager: AccessManager.Listener, AccessManager.TokenUpdateListener {
    override fun onTokenExpired(accessManager: AccessManager) {
        Dependencies.DEPENDENCIES.userPrefs.twilioToken = accessManager.token
        Log.e("LOG_TAG---", "onTokenExpired: ")
    }

    override fun onTokenWillExpire(accessManager: AccessManager) {
//        accessManager.updateToken(accessManager.)
        Log.e("LOG_TAG---", "onTokenWillExpire: ")
    }

    override fun onError(p0: AccessManager?, p1: String?) {
        Log.e("LOG_TAG---", "onError: ")
    }

    override fun onTokenUpdated(updatedToken: String) {
        Dependencies.DEPENDENCIES.userPrefs.twilioToken = updatedToken
        Log.e("LOG_TAG---", "onTokenUpdated: ")
    }
}