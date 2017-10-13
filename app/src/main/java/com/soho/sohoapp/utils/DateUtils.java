package com.soho.sohoapp.utils;

import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public final class DateUtils {
    private static final String FORMAT_FOR_FILE_NAME = "yyyyMMdd_HHmmss";
    private static final String FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String FORMAT_DISPLAYABLE = "dd MMM yyyy";
    private static final String FORMAT_TIME = "hh:mma";

    private DateUtils() {
        //utility class
    }

    public static String getDateFormatForFileName() {
        return new SimpleDateFormat(FORMAT_FOR_FILE_NAME, Locale.getDefault()).format(new Date());
    }

    public static String toInspectionDurationString(@Nullable Long startTime, @Nullable Long endTime) {
        if (startTime == null || endTime == null) {
            return "";
        }
        String start = new SimpleDateFormat(FORMAT_TIME, Locale.getDefault()).format(new Date(startTime));
        String end = new SimpleDateFormat(FORMAT_TIME, Locale.getDefault()).format(new Date(endTime));
        return String.format("%s - %s", start, end);
    }

    public static String timeToDisplayableString(long time) {
        return new SimpleDateFormat(FORMAT_DISPLAYABLE, Locale.getDefault()).format(new Date(time));
    }

    public static String timeToQueryString(long time) {
        return new SimpleDateFormat(FORMAT_ISO8601, Locale.getDefault()).format(new Date(time));
    }

    public static Long iso8601TimeToLong(String time) {
        Long result = null;
        if (time != null && !time.isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_ISO8601, Locale.getDefault());
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                result = simpleDateFormat.parse(time).getTime();
            } catch (ParseException e) {
                DEPENDENCIES.getLogger().e("Error during parsing a date", e);
            }
        }
        return result;
    }

    public static long toTimeInMillis(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar.getTimeInMillis();
    }

}
