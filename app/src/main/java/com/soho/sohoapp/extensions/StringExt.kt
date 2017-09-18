package com.soho.sohoapp.extensions

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
fun String.toDate(stringFormat:String): Date? {
    val format:SimpleDateFormat = SimpleDateFormat(stringFormat)
    try {
        return format.parse(this)
    } catch (e: ParseException) {
        return null
    }
}

/**
 * Returns a long date value of the string if it matches the 'stringFormat' specified
 * @param stringFormat the expected date format to parse
 * @return the date object after string has been parsed (Nullable)
 */
fun String.toDateLong(stringFormat:String): Long {
    return this.toDate(stringFormat)?.time ?: 0
}

// MARK: - ================== Convenience methods for common formats ==================

/**
 * Returns a date object of the string if it matches the 'stringFormat' specified, otherwise null
 * @return returns date if string is in 'YYYY-MM-dd'T'HH:mm:ss.SSSZ' format (Nullable)
 */
fun String.toDateWithIso8601DateTimeFormat(): Date? {
    return this.toDate(DateFormat.Iso8601DateTime().stringFormat())
}

/**
 * Returns a date Long of the string if it matches the 'stringFormat' specified
 * @return returns Long representation of string date
 */
fun String.toDateLongWithIso8601DateTimeFormat(): Long {
    return this.toDateLong(DateFormat.Iso8601DateTime().stringFormat())
}