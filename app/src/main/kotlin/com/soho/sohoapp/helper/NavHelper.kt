package com.soho.sohoapp.helper

import android.app.Activity
import android.content.Intent
import com.soho.sohoapp.feature.landing.signup.RegisterUserInfoActivity
import com.soho.sohoapp.feature.landing.LandingActivity
import com.soho.sohoapp.feature.landing.signup.SignUpActivity

/**
 * Created by chowii on 25/7/17.
 */
class NavHelper {


    companion object{

        fun showLandingActivity(activity: Activity, error: String){
            activity.startActivity(
                    Intent(activity, LandingActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

        fun showRegisterUserInfoActivity(activity: SignUpActivity) {
            activity.startActivity(
                    Intent(activity, RegisterUserInfoActivity::class.java)
            )
        }


    }

}
