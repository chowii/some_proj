package com.soho.sohoapp.feature.network

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.helper.SohoSnackbar
import com.soho.sohoapp.navigator.NavigatorImpl
import com.soho.sohoapp.network.HttpErrorType
import com.soho.sohoapp.network.HttpStatusException

/**
 * Created by mariolopez on 20/9/17.
 */
class ThrowableHandler {
    companion object {

        @JvmStatic
        fun showError(throwable: Throwable
                      , showInternetErrors: Boolean = true
                      , coordinatorLayout: FrameLayout? = null
                      , snackbarAnchorView: View?
                      , activity: Activity) {
            if (throwable is HttpStatusException) {
                when (throwable.errorType) {
                    HttpErrorType.General -> {
                        if (showInternetErrors) {
                            SohoSnackbar().showSnackbar(snackbarAnchorView, activity.getString(R.string.something_wrong_error))
                        }
                    }

                    HttpErrorType.PasswordReEnterRequired,
                    HttpErrorType.PasswordResetRequired,
                    HttpErrorType.ReloginRequired ->
                        NavigatorImpl.newInstance(activity).openLandingActivity()
                }
            } else if (showInternetErrors && snackbarAnchorView != null) {
                SohoSnackbar().showSnackbar(snackbarAnchorView, activity.getString(R.string.something_wrong_error))
            }
            handleCustomError(throwable, activity.getString(R.string.something_wrong_error))
        }

        private fun handleCustomError(throwable: Throwable, errorMessage: String) {
            DEPENDENCIES.logger.e(errorMessage, throwable)
        }
    }
}
