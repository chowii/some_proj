package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable
import com.soho.sohoapp.helper.DateHelper

import java.util.Calendar

/**
 * Created by chowii on 5/9/17.
 */

class InspectionTime() : Parcelable {

    var id: Int = 0
    var startTime: Long = 0
    var endTime: Long = 0

//    fun retrieveStartTime(): Calendar {
//        if (startTime == null) return Calendar.getInstance()
//        return DateHelper.retrieveCalendarFromApiDate(startTime)
//    }
//
//    fun retrieveDisplayableStartTime(): String {
//        return DateHelper.retrieveShortDisplayableTime(retrieveStartTime())
//    }
//
//    fun retrieveDisplayableStartDate(): String {
//        return DateHelper.retrieveShortDisplayableDate(retrieveStartTime())
//    }
//
//    fun retrieveEndTime(): Calendar {
//        if (endTime == null) return Calendar.getInstance()
//        return DateHelper.retrieveCalendarFromApiDate(endTime)
//    }
//
//    fun retrieveDisplayableEndTime(): String {
//        return DateHelper.retrieveShortDisplayableTime(retrieveEndTime())
//    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        startTime = parcel.readLong()
        endTime = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeLong(startTime)
        parcel.writeLong(endTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InspectionTime> {
        override fun createFromParcel(parcel: Parcel): InspectionTime {
            return InspectionTime(parcel)
        }

        override fun newArray(size: Int): Array<InspectionTime?> {
            return arrayOfNulls(size)
        }
    }

}
