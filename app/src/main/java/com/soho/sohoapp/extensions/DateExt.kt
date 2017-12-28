package com.soho.sohoapp.extensions

import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.SohoApplication
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

/**
 * Created by Jovan on 18/9/17.
 */

// MARK: - ================== Common formats used ==================

sealed class DateFormat {

    class Iso8601DateTime : DateFormat()
    class MonthAbbreviationFormat : DateFormat()
    class DateDisplayFormat : DateFormat()
    class DateTimeShort : DateFormat()
    class TimeFormat : DateFormat()

}

fun DateFormat.stringFormat(): String {
    val format = when (this) {
        is DateFormat.Iso8601DateTime -> "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        is DateFormat.MonthAbbreviationFormat -> "dd MMM yyyy"
        is DateFormat.DateDisplayFormat -> "dd/MM/yy"
        is DateFormat.TimeFormat -> "h:mma"
        is DateFormat.DateTimeShort -> "MMM dd, h:mm a"
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
    val format: SimpleDateFormat = SimpleDateFormat(stringFormat, Locale.getDefault())
    if (stringFormat == DateFormat.Iso8601DateTime().stringFormat()) {
        format.timeZone = TimeZone.getTimeZone("UTC")
    }
    try {
        return format.format(this)
    } catch (e: ParseException) {
        DEPENDENCIES.logger.e("Exception during parsing date to string", e)
        return ""
    }
}

fun Date.durationFromNow(): Date {
    val durationLong = System.currentTimeMillis() - this.time
    return Date(durationLong)
}


fun Date.durationFromNowAsString(): String {
    val duration = this.durationFromNow()
    Calendar.getInstance().let {
        it.time = duration
        val time = when {
            it[Calendar.DAY_OF_YEAR] < 1 -> getHourDuration(it)
            it[DAY_OF_YEAR] < 29 -> getDayDuration(it)
            else -> getMonthDuration(it)
        }
        return time
    }
}

private fun getMonthDuration(it: Calendar) = SohoApplication.getContext()
        .resources.getQuantityString(R.plurals.month, it[MONTH], it[MONTH])

private fun getDayDuration(calendar: Calendar) = SohoApplication.getContext()
        .resources.getQuantityString(R.plurals.month, calendar[MONTH], calendar[MONTH])

private fun getHourDuration(calendar: Calendar): String {
    val time = calendar[HOUR]
    return if (time < 0)
        calendar.get(MINUTE).toString()
    else
        time.toString()
}
