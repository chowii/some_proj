package com.soho.sohoapp.extensions

import android.content.res.Resources
import android.util.DisplayMetrics
import com.soho.sohoapp.R
import com.soho.sohoapp.SohoApplication.getStringFromResource
import java.text.DecimalFormat

/**
 * Created by Jovan on 21/9/17.
 */

fun Int.toShortHand(): String {
    val format = DecimalFormat(getStringFromResource(R.string.int_ext_shorthand_format))
    return when {
        this >= 1_000_000_000 -> format.format(this.toDouble() / 1_000_000_000.0) + getStringFromResource(R.string.int_ext_shorthand_billion)
        this >= 1_000_000.0 -> format.format((this.toDouble()) / 1_000_000.0) + getStringFromResource(R.string.int_ext_shorthand_million)
        this >= 1_000 -> format.format((this.toDouble() / 1_000.0)) + getStringFromResource(R.string.int_ext_shorthand_thousand)
        else -> "$this"
    }
}

fun Int.dpToPx(resources: Resources): Int =
        Math.round(this * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))