package com.soho.sohoapp.dev.feature.landing

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.dev.R
import com.soho.sohoapp.dev.feature.landing.signup.SignUpActivity


/**
 * Created by chowii on 25/7/17.
 */
class LandingActivity: AppCompatActivity() {

    @OnClick(R.id.signup)
    fun onSignUpClicked(){
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        ButterKnife.bind(this)
    }

}