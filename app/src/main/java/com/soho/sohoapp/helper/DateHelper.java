package com.soho.sohoapp.helper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class DateHelper {

    private static final SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.US);
    private static final SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat apiDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
    public static final String unixTimeStampFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault());
    private static final SimpleDateFormat dMMYYYY = new SimpleDateFormat("d MM yyyy", Locale.getDefault());
    public static final SimpleDateFormat ddMMMYYYY = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    public static final SimpleDateFormat timeDateDisplayFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss a", Locale.getDefault());
    private static final GregorianCalendar calendar = new GregorianCalendar();
    private static final SimpleDateFormat displayDateTimeFormat = new SimpleDateFormat("dd/MM/yy h:mm a", Locale.US);
    private static final String TAG = "DateHelper";

    public static final int TIMELINE_EITHER = 0;
    private static final int TIMELINE_PAST = 1;
    private static final int TIMELINE_FUTURE = 2;

    public static String dateTodMMyyyy(Date d) {
        return dMMYYYY.format(d);
    }

    public static String toApiDate(String s) {
        if (s.isEmpty()) {
            return "";
        }

        try {
            Date date = displayDateFormat.parse(s);
            return apiDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }

    public static String toApiDateTime(String s) {
        if (s.isEmpty()) {
            return "";
        }

        try {
            Date date = displayDateTimeFormat.parse(s);
            return apiDateTimeFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Nullable
    public static Date stringToDate(String dateString, String formatString) {
        if (dateString == null || formatString == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.getDefault());
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void showPicker(Context context, final TextView v) {
//        showPicker(context, v); this cause infinite recursion fix when the method is used
    }

    public static void showFuturePicker(Context context, final TextView v) {
        Calendar today = Calendar.getInstance();
        getDatePickerDialog(context, v, today, TIMELINE_FUTURE, null).show();
    }

    /**
     * @param v            the textView where to show the date
     * @param date         must be of the form dd/mm/yyyy
     * @param pastOrFuture must of the form DateHelper.FUTURE or DateHelper.PAST. can be 0 if does not
     *                     past and future allowed
     * @return              Date picker dialog
     */
    public static DatePickerDialog getDatePickerDialog(Context context, final TextView v, final Calendar date,
                                                       int pastOrFuture, DatePickerDialog.OnDateSetListener pListener) {

        DatePickerDialog.OnDateSetListener listener;

        if (pListener == null) {
            listener = (view, year, monthOfYear, dayOfMonth) -> {
                calendar.set(year, monthOfYear, dayOfMonth);
                v.setText(displayDateFormat.format(calendar.getTime()));
            };
        } else {
            listener = pListener;
        }

        // If our textView isn't update, use the passed date or today's date if nothing is passed
        Calendar cal = null;
        if (date != null) {
            cal = date;
        }
        if (cal == null) {
            cal = Calendar.getInstance();
        }
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        if (!v.getText().toString().isEmpty()) {
            try {
                calendar.setTime(displayDateFormat.parse(v.getText().toString()));
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        DatePickerDialog dialog = new DatePickerDialog(context, listener, year, month, day);
        if (pastOrFuture != 0) {
            Calendar today = Calendar.getInstance();
            if (pastOrFuture == TIMELINE_PAST) {
                if (today.compareTo(cal) <= 0) // today is before cal
                    dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                else dialog.getDatePicker().setMaxDate(today.getTimeInMillis());
            }
            if (pastOrFuture == TIMELINE_FUTURE) {
                if (today.compareTo(cal) <= 0)//today is before cal
                    dialog.getDatePicker().setMaxDate(today.getTimeInMillis());
                else dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            }
        }

        return dialog;
    }

    public static void showTimePikerDialog(Context context, final TextView v, final int pastFuture) {

        final GregorianCalendar now = new GregorianCalendar();

        TimePickerDialog.OnTimeSetListener listener = (view, hourOfDay, minute) -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.set(year, month, day, hourOfDay, minute);
            switch (pastFuture) {
                case TIMELINE_FUTURE:
                    if (!isDateInPast(calendar)) {
                        v.setText(displayDateTimeFormat.format(calendar.getTime()));
                    } else {

                        v.setText(displayDateTimeFormat.format(now.getTime()));
                    }
                    break;
                case TIMELINE_PAST:
                    if (isDateInPast(calendar)) {
                        v.setText(displayDateTimeFormat.format(calendar.getTime()));
                    } else {

                        v.setText(displayDateTimeFormat.format(now.getTime()));
                    }
                    break;
                default:
                    v.setText(displayDateTimeFormat.format(calendar.getTime()));
            }
        };

        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(context, listener, hour, minute, false);
        dialog.show();
    }

    public static boolean isDateInPast(GregorianCalendar calendar) {
        GregorianCalendar now = new GregorianCalendar();
        return calendar.getTimeInMillis() < now.getTimeInMillis();
    }

    /**
     * Show a date picker and on callback show a time picker
     *
     * @param context
     * @param v            the textView where to show the date
     * @param pastFuture of the Format DateHelper.TIMELINE...
     */
    public static void showDateTimeDialog(final Context context, final TextView v, final int pastFuture) {

        DatePickerDialog.OnDateSetListener listener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(year, monthOfYear, dayOfMonth);
            showTimePikerDialog(context, v, pastFuture);
        };

        DatePickerDialog datePickerDialog = getDatePickerDialog(context, v, null, pastFuture, listener);
        datePickerDialog.show();
    }


    /**
     * Takes a text in format d MMM YYYY and return a string formated dd/MM/yyyy
     *
     * @param text must be of the form d MMM YYYY
     * @return the Api formatted string
     */
    public static String dateForAPICall(String text) {

        if (text.isEmpty()) {
            return "";
        }

        try {
            Date date = displayDateFormat.parse(text);
            return apiDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param text must be of the form dd/mm/yyyy
     * @return Calendar from date string
     */
    public static Calendar getCalendarFromString(String text) {
        try {
            Date date = apiDateFormat.parse(text);
            return apiDateFormat.getCalendar();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a date set to yesterday at the same time as now
     *
     * @return the corresponding String in the format d MMM YYYY
     */
    public static String getYesterdayAsText() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);

        return displayDateFormat.format(yesterday.getTime());
    }


    /**
     * @param stringDate
     * @return displayable string for the date returned from the api
     */
    public static String getDisplayableStringFromStringDateReceivedFromAPI(String stringDate) {
        try {
            Date date = apiDateTimeFormat.parse(stringDate);
            return displayDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Takes a date in the Format d/MM/yyy (ex 01/02/2016) and returns a string of the format
     * d MMM yyyyy
     *
     * @return a valid date to display or an empty string if a parse exception occurred.
     */
    public static String getDisplayableDateFromAPIDateFormat(String date) {
        Date formattedDate;
        try {
            formattedDate = apiDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return displayDateFormat.format(formattedDate);
    }

    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400
    public static String getTimeUntilString(Date startDate, Date endDate) {

        String string = "";
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        if (elapsedDays > 0) { string += elapsedDays + " d" + (elapsedDays == 1 ? "" : "s") + "\n"; }
        if (elapsedHours > 0) { string += elapsedHours + " hr" + (elapsedHours == 1 ? "" : "s") + "\n"; }
        if (elapsedMinutes > 0) { string += elapsedMinutes + " min" + (elapsedMinutes == 1 ? "" : "s") + "\n"; }
        if(string.length() >= 2) {
            string = string.substring(0, string.length() - 3);
        } else {
            string = "NA";
        }
        return string;
    }


    public static String toDate(long t1){
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(t1);
        String toDate = "";
        toDate += c1.get(Calendar.DAY_OF_MONTH) + " ";
        toDate += intToStringMonthLong(c1.get(MONTH)) + " ";
        toDate += c1.get(YEAR);

        return toDate;
    }

    public static String durationByMonth(long t1, long t2){
        Calendar d1 = Calendar.getInstance();
        d1.setTimeInMillis(t1);

        Calendar d2 = Calendar.getInstance();
        d2.setTimeInMillis(t2);

        long l = (t2-t1)/(3600 * 24 * 1000);

        String m1 = intToStringMonthLong(d1.get(MONTH));
        String m2 = intToStringMonthLong(d2.get(MONTH));

        return m1 + " - " + m2 + " " + d2.get(YEAR) + ", " + (l/30) + " months";
    }

    public static String intToStringMonthLong(int month){
        switch(month){
            case 0: return "January";
            case 1: return "February";
            case 2: return "March";
            case 3: return "April";
            case 4: return "May";
            case 5: return "June";
            case 6: return "July";
            case 7: return "August";
            case 8: return "September";
            case 9: return "October";
            case 10: return "November";
            default: return "December";
        }
    }

    public static boolean isDateToday(long time) {
        if (time < 1500000000L) return false;

        Calendar[] c = initCalendar((time * 1000));
        Calendar today = c[0];
        Calendar timeProvided = c[1];

        if(today.get(Calendar.YEAR) == timeProvided.get(Calendar.YEAR)){
            if(today.get(Calendar.DAY_OF_YEAR) == timeProvided.get(Calendar.DAY_OF_YEAR))
                return true;
        }
        return false;
    }

    public static boolean isDateTomorrow(long time) {
        if (time < 1500000000L) return false;

        Calendar[] c = initCalendar((time * 1000));
        Calendar today = c[0];
        Calendar timeProvided = c[1];


        if(today.get(Calendar.YEAR) == timeProvided.get(Calendar.YEAR)){
            int todayDayOfYear =   today.get(Calendar.DAY_OF_YEAR);
            todayDayOfYear = todayDayOfYear + 1;

            int timeProvidedDayOfYear = timeProvided.get(Calendar.DAY_OF_YEAR);
            if(todayDayOfYear == timeProvidedDayOfYear)
                return true;
        }
        return false;
    }

    public static boolean isDateInFuture(long time) {
        if (time < 1500000000L) return false;

        Calendar[] c = initCalendar((time * 1000));
        Calendar timeProvided = c[0];
        Calendar today = c[1];

        if(today.get(Calendar.YEAR) == timeProvided.get(Calendar.YEAR)){
            int todayDayOfYear =   today.get(Calendar.DAY_OF_YEAR);
            todayDayOfYear = todayDayOfYear + 1;

            int timeProvidedDayOfYear = timeProvided.get(Calendar.DAY_OF_YEAR);
            if(todayDayOfYear > timeProvidedDayOfYear)
                return true;
        }
        return false;
    }


    private static Calendar[] initCalendar(long... time){
        long timeProvided = time[0];
        Calendar providedCalendar = Calendar.getInstance();
        Calendar todayCalendar = Calendar.getInstance();

        providedCalendar.setTimeInMillis(timeProvided);
        todayCalendar.setTimeInMillis(System.currentTimeMillis());


        return new Calendar[]{ todayCalendar, providedCalendar};
    }
}
