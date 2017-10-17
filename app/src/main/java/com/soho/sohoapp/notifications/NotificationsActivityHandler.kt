package com.soho.sohoapp.notifications

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.soho.sohoapp.navigator.NavigatorImpl

/**
 * Created by mariolopez on 17/10/17.
 */
class NotificationsActivityHandler : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navigator = NavigatorImpl.newInstance(this)

        if (intent.data == null) {
            navigator.openHomeActivity()
        } else {
            navigator.openHomeActivityDeepLinking(intent.data)
        }
        navigator.exitCurrentScreen()
    }
}