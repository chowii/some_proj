package com.soho.sohoapp

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.soho.sohoapp.abs.AbsActivity
import com.soho.sohoapp.navigator.NavigatorImpl




class SplashActivity : AbsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val navigator = NavigatorImpl.newInstance(this)
        if (!isUserSignedIn) {
            navigator.openLandingActivityFromSplash()
        } else {
            navigator.openHomeActivity()
        }
    }
}