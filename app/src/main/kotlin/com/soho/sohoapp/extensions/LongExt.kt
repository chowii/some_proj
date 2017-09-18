package com.soho.sohoapp.extensions

import java.util.*

/**
 * Created by Jovan on 18/9/17.
 */

/**
 * Returns a string representation in the format specified of the long date
 * @param format format of output string
 * @return returns string representation of long date
 */
fun Long.stringWithFormat(format:String): String {
    return Date(this).toStringWithFormat(format)
}

// MARK: - ================== Common formats ==================

/**
 * Returns a string representation in the format specified of the long date
 * @return returns string representation of long date in 'YYYY-MM-dd'T'HH:mm:ss.SSSZ' format
 */
fun Long.toDateLongWithIso8601DateTimeFormat(): String {
    return Date(this).toStringWithFormat(DateFormat.Iso8601DateTime().stringFormat())
}

/**
 * Returns a string representation in the format specified of the long date
 * @return returns string representation of long date in 'dd/MM/YY' format
 */
fun Long.toStringWithDisplayFormat(): String {
    return Date(this).toStringWithFormat(DateFormat.DateDisplayFormat().stringFormat())
}

/**
 * Returns a string representation in the format specified of the long date
 * @return returns string representation of long date in 'dd/MM/YY' format
 */
fun Long.toStringWithTimeFormat(): String {
    return Date(this).toStringWithFormat(DateFormat.DateDisplayFormat().stringFormat())
}