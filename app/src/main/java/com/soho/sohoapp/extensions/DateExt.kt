package com.soho.sohoapp.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jovan on 18/9/17.
 */

// MARK: - ================== Common formats used ==================

sealed class DateFormat {

    class Iso8601DateTime : DateFormat()
    class MonthAbbreviationFormat : DateFormat()
    class DateDisplayFormat : DateFormat()
    class TimeFormat : DateFormat()

}

fun DateFormat.stringFormat(): String {
    val format = when (this) {
        is DateFormat.Iso8601DateTime -> "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        is DateFormat.MonthAbbreviationFormat -> "dd MMM yyyy"
        is DateFormat.DateDisplayFormat -> "dd/MM/yy"
        is DateFormat.TimeFormat -> "h:mma"
    }
    return format
}

// MARK: - ================== Methods ==================

/**
 * Returns a string representation of the date
 * @param stringFormat the expected date format to parse
 * @return string representation of date
 */
fun Date.toStringWithFormat(stringFormat: String): String {
    val format: SimpleDateFormat = SimpleDateFormat(stringFormat)
    try {
        return format.format(this)
    } catch (e: ParseException) {
        return ""
    }
}
