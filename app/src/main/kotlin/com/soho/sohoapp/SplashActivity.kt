package com.soho.sohoapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.soho.sohoapp.feature.home.HomeActivity
import com.soho.sohoapp.feature.landing.LandingActivity
import com.soho.sohoapp.helper.SharedPrefsHelper

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        if (SharedPrefsHelper.getInstance().authToken == "") {
            startActivity(Intent(this, LandingActivity::class.java))
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        finish()
    }

}