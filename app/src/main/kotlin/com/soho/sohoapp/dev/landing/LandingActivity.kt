package com.soho.sohoapp.dev.landing

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.soho.sohoapp.dev.R


/**
 * Created by chowii on 25/7/17.
 */
class LandingActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, LandingFragment.newInstance())
                .commit()
    }

}