package com.soho.sohoapp.feature.landing

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.landing.signup.SignUpActivity
import com.soho.sohoapp.helper.NavHelper


/**
 * Created by chowii on 25/7/17.
 */
class LandingActivity: AppCompatActivity() {

    @OnClick(R.id.signup)
    fun onSignUpClicked(){
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    @OnClick(R.id.login)
    fun onLoginClicked() {
        startActivity(Intent(this, LoginActivity::class.java))
            }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        ButterKnife.bind(this)
    }

}