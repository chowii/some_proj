package com.soho.sohoapp.extensions

import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.SohoApplication.getStringFromResource
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jovan on 18/9/17.
s */

/**
 * Returns a date object of the string if it matches the 'stringFormat' specified, otherwise null
 * @param stringFormat the expected date format to parse
 * @return the date object after string has been parsed (Nullable)
 */
fun String.toDate(stringFormat: String): Date? {
    val format: SimpleDateFormat = SimpleDateFormat(stringFormat, Locale.getDefault())
    if (stringFormat == DateFormat.Iso8601DateTime().stringFormat()) {
        format.timeZone = TimeZone.getTimeZone("UTC")
    }
    try {
        return format.parse(this)
    } catch (e: ParseException) {
        DEPENDENCIES.logger.e("Exception during parsing string to date", e)
        return null
    }
}

/**
 * Returns a long date value of the string if it matches the 'stringFormat' specified
 * @param stringFormat the expected date format to parse
 * @return the date object after string has been parsed (Nullable)
 */
fun String.toDateLong(stringFormat: String) = this.toDate(stringFormat)?.time

// MARK: - ================== Convenience methods for common formats ==================

/**
 * Returns a date object of the string if it matches the 'stringFormat' specified, otherwise null
 * @return returns date if string is in 'YYYY-MM-dd'T'HH:mm:ss.SSSZ' format (Nullable)
 */
fun String.toDateWithIso8601DateTimeFormat() = this.toDate(DateFormat.Iso8601DateTime().stringFormat())

/**
 * Returns a date Long of the string if it matches the 'stringFormat' specified
 * @return returns Long representation of string date
 */
fun String.toDateLongWithIso8601DateTimeFormat() = this.toDateLong(DateFormat.Iso8601DateTime().stringFormat())

/**
 * Returns Double value of the string if it is possibly
 * @return returns Double value of string
 */
fun String.toDoubleOrDefault(default: Double): Double {
    try {
        return this.trim().toDouble()
    } catch (e: NumberFormatException) {
        DEPENDENCIES.logger.e("Exception during parsing string to date")
        return default
    }
}

/**
 * Returns an abbreviated string representation of a large value e.g. $1K -> 1000
 * @return int value of string
 */
fun String.abbreviatedMoneyValueToInt(): Int {
    try {
        val strippedString = this.replace("$", "")
        if (strippedString.contains(getStringFromResource(R.string.int_ext_shorthand_thousand), true)) {
            return strippedString.replace(getStringFromResource(R.string.int_ext_shorthand_thousand), "", true).toInt() * 1_000
        } else if (strippedString.contains(getStringFromResource(R.string.int_ext_shorthand_million), true)) {
            return strippedString.replace(getStringFromResource(R.string.int_ext_shorthand_million), "", true).toInt() * 1_000_000
        } else if (strippedString.contains(getStringFromResource(R.string.int_ext_shorthand_billion), true)) {
            return strippedString.replace(getStringFromResource(R.string.int_ext_shorthand_billion), "", true).toInt() * 1_000_000_000
        }
        return strippedString.toInt()
    } catch (exception: NumberFormatException) {
        return 0
    }
}

//note useless to localize instead use Currency class with Locale
fun String.withCurrency() = "$" + this