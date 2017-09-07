package com.soho.sohoapp.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.soho.sohoapp.feature.home.HomeActivity
import com.soho.sohoapp.feature.landing.ForgotPasswordActivity
import com.soho.sohoapp.feature.landing.LandingActivity
import com.soho.sohoapp.feature.landing.LoginActivity
import com.soho.sohoapp.feature.landing.signup.RegisterUserInfoActivity
import com.soho.sohoapp.feature.landing.signup.SignUpActivity

/**
 * Created by chowii on 25/7/17.
 */
class NavHelper {

    companion object {

        fun showLandingActivity(activity: Activity, error: String) = activity.startActivity(
                Intent(activity, LandingActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )

        fun showRegisterUserInfoActivity(activity: Activity) = activity.startActivity(
                Intent(activity, RegisterUserInfoActivity::class.java)
        )

        fun showLoginActivity(activity: Activity) = activity.startActivity(
                Intent(activity, LoginActivity::class.java)
        )

        fun showSignUpActivity(activity: Activity) = activity.startActivity(
                Intent(activity, SignUpActivity::class.java)
        )

        fun showForgotPasswordActivity(activity: Activity) = activity.startActivity(
                Intent(activity, ForgotPasswordActivity::class.java)
        )

        fun showHomeActivityAndClearTasks(context: Context) = context
                .startActivity(
                        Intent(context, HomeActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

}
