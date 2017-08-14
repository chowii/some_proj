package com.soho.sohoapp

import android.content.Context

/**
 * Created by chowii on 25/7/17.
 */
class Constants {

    companion object {
        @JvmStatic
        var ENDPOINT: String? = null

        @JvmStatic
        fun init(context: Context): Unit { ENDPOINT = context.getString(R.string.endpoint) }
    }

}