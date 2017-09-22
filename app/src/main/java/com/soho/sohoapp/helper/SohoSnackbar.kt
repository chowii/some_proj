package com.soho.sohoapp.helper

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by chowii on 25/7/17.
 */
class SohoSnackbar {

    fun showSnackbar(view: View?, message: String) {
        view?.let {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show() //if we show from top it suffers of wrong animation

        }
    }

}