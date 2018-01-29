package com.soho.sohoapp.notifications

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.soho.sohoapp.Dependencies

/**
 * Created by chowii on 23/1/18.
 */
class FcmInstanceIdService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        Dependencies.DEPENDENCIES.userPrefs.fcmPushNotificationToken = FirebaseInstanceId.getInstance().token.orEmpty()
    }
}