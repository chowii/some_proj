package com.soho.sohoapp.abs

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.View
import android.widget.Toast
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.feature.network.ThrowableHandler
import com.soho.sohoapp.logger.Logger

abstract class AbsActivity : AppCompatActivity() {
    protected val logger: Logger = DEPENDENCIES.logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected open fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun showToast(@StringRes resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    protected val isUserSignedIn: Boolean
        get() {
            val prefs = DEPENDENCIES.preferences
            return !prefs.authToken.isEmpty()
        }

    protected fun handleError(throwable: Throwable = Exception()) {
        ThrowableHandler.showError(throwable, true, null, findViewById<View>(android.R.id.content), this)
    }

    companion object {

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

}
