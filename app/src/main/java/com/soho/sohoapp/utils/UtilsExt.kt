package com.soho.sohoapp.utils

import android.util.Patterns
import android.widget.Button

/**
 * Created by mariolopez on 28/9/17.
 */
fun Boolean?.orFalse(): Boolean = this ?: false

/**
 * @return return if the string is an email format
 */
fun String?.isEmail(): Boolean {
    if (this == null) {
        return false
    }
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * @return return if the string is an email format
 */
fun CharSequence?.isEmail(): Boolean = this.toString().isEmail()


fun Button.checkEnableDisableAlpha(enablingCondition: Boolean) {
    this.alpha = if (enablingCondition) 1f else 0.4f
    this.isEnabled = enablingCondition
}
