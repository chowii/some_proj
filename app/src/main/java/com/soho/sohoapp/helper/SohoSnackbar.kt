package com.soho.sohoapp.helper

import android.support.design.widget.Snackbar
import android.view.Gravity.TOP
import android.view.View
import android.widget.FrameLayout

/**
 * Created by chowii on 25/7/17.
 */
class SohoSnackbar {

    fun showSnackbar(view: View?, message: String) {
        view?.let {
            val message: Snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            val snackbarView: View = message.view
            val params: FrameLayout.LayoutParams = snackbarView.layoutParams as FrameLayout.LayoutParams
            params.gravity = TOP
            snackbarView.layoutParams = params
            message.show()
        }
    }

}