package com.soho.sohoapp.extensions

import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.R
import com.soho.sohoapp.SohoApplication
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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
    val adjustIndex = 1
    val duration = this.durationFromNow()
    Calendar.getInstance().let {
        it.time = duration
        return getDuration(it.timeInMillis)

    }
}

private fun getDuration(timeInMillis: Long): String {
    val resources = SohoApplication.getContext().resources
    val milliseconds = TimeUnit.MILLISECONDS
    val seconds = milliseconds.toSeconds(timeInMillis)
    val minutes = milliseconds.toMinutes(timeInMillis)
    val hours = milliseconds.toHours(timeInMillis)
    val days = milliseconds.toDays(timeInMillis)

    return if (seconds < 60)
        "Just now"
    else if (minutes < 60)
        resources.getQuantityString(R.plurals.minute, minutes.toInt(), minutes)
    else if (hours < 24)
        resources.getQuantityString(R.plurals.hour, hours.toInt(), hours)
    else if (days < 30)
        resources.getQuantityString(R.plurals.day, days.toInt(), days)
    else {
        val month = days / 30
        if (month < 12)
            resources.getQuantityString(R.plurals.month, month.toInt(), month)
        else {
            val year = month / 12
            resources.getQuantityString(R.plurals.year, year.toInt(), year)
        }
    }
}

fun Date.currentUtcDateTimeStamp(format: String): String {
    val c = Calendar.getInstance()
    c.timeInMillis = System.currentTimeMillis()
    c.timeZone = TimeZone.getDefault()
    return String.format(
            Locale.getDefault(),
            format,
            c.get(Calendar.YEAR),
            c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
            c.get(Calendar.DAY_OF_MONTH),
            Calendar.HOUR,
            Calendar.MINUTE,
            Calendar.SECOND,
            Calendar.MILLISECOND
    )

}

