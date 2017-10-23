package com.soho.sohoapp.extensions

import android.os.Bundle
import android.os.Parcelable

/**
 * Created by Jovan on 10/10/17.
 */

fun <T> Bundle.getParcelableOrNull(key: String): T? where T : Parcelable =
        (if (containsKey(key)) getParcelable(key) else null)
