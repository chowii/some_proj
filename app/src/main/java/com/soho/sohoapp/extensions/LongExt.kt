package com.soho.sohoapp.extensions

import com.soho.sohoapp.extensions.DateFormat.*
import java.util.*

/**
 * Created by Jovan on 18/9/17.
 */

/**
 * Returns a string representation in the format specified of the long date
 * @param format format of output string
 * @return returns string representation of long date
 */
fun Long.stringWithFormat(format: String) =
        Date(this).toStringWithFormat(format)

// MARK: - ================== Common formats ==================

/**
 * Returns a string representation in the format specified of the long date
 * @return returns string representation of long date in 'YYYY-MM-dd'T'HH:mm:ss.SSSZ' format
 */
fun Long?.toDateLongWithIso8601DateTimeFormat() =
        toStringWithFormat(this, Iso8601DateTime())

/**
 * Returns a string representation in the format specified of the long date
 * @return returns string representation of long date in 'dd/MM/YY' format
 */
fun Long?.toStringWithDisplayFormat() =
        toStringWithFormat(this, DateDisplayFormat())

/**
 * Returns a string representation in the format specified of the long date
 * @return returns string representation of long date in 'dd/MM/YY' format
 */
fun Long?.toStringWithTimeFormat() =
        toStringWithFormat(this, TimeFormat())

private fun toStringWithFormat(value: Long?, dateFormat: DateFormat)
        = if (value != null) Date(value).toStringWithFormat(dateFormat.stringFormat()) else ""
