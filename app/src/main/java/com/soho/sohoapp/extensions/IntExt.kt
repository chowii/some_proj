package com.soho.sohoapp.extensions

import com.soho.sohoapp.R
import com.soho.sohoapp.SohoApplication
import java.text.DecimalFormat

/**
 * Created by Jovan on 21/9/17.
 */

fun Int.toShortHand(): String {
    val format = DecimalFormat(SohoApplication.getStringFromResource(R.string.int_ext_shorthand_format))
    return when {
        this >= 1_000_000_000 -> "" + format.format(this.toDouble() / 1_000_000_000.0) + SohoApplication.getStringFromResource(R.string.int_ext_shorthand_billion)
        this >= 1_000_000.0 -> "" + format.format((this.toDouble()) / 1_000_000.0) + SohoApplication.getStringFromResource(R.string.int_ext_shorthand_million)
        this >= 1_000 -> "" + format.format((this.toDouble() / 1_000.0)) + SohoApplication.getStringFromResource(R.string.int_ext_shorthand_thousand)
        else -> "$this"
    }
}