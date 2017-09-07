package com.soho.sohoapp.data;

import com.google.gson.annotations.SerializedName;
import com.soho.sohoapp.helper.DateHelper;

import java.util.Calendar;

/**
 * Created by chowii on 5/9/17.
 */

public class PropertyInspectionTime {

    private int id;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    public Calendar retrieveStartTime() {
        if(startTime == null) return Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(DateHelper.stringToDate(startTime == null ? "" : startTime, "yyyy-MM-dd'T'HH:mm:ss.SSS"));
        return startCalendar;
    }

    public String retrieveDisplayableStartTime(){
        return DateHelper.retrieveShortDisplayableTime(retrieveStartTime());
    }

    public String retrieveDisplayableStartDate(){
       return DateHelper.retrieveShortDisplayableDate(retrieveStartTime());
    }

    public void applyStartTimeChange(String startTime) {
        this.startTime = startTime;
    }

    public Calendar retrieveEndTime() {
        if(endTime == null) return Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(DateHelper.stringToDate(endTime == null ? "" : endTime, "yyyy-MM-dd'T'HH:mm:ss.SSS"));
        return startCalendar;
    }

    public String retrieveDisplayableEndTime(){
        return DateHelper.retrieveShortDisplayableTime(retrieveEndTime());
    }

    public void applyEndTimeChange(String endTime) { this.endTime = endTime; }
}
