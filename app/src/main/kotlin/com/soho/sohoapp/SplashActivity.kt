package com.soho.sohoapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.soho.sohoapp.feature.landing.LandingActivity

class SplashActivity : AppCompatActivity() {

    internal var SPLASH_TIME_OUT: Int = 2000;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(
                    Intent(this, LandingActivity::class.java))
        },
                SPLASH_TIME_OUT.toLong())
    }
}
