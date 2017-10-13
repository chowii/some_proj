package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by chowii on 5/9/17.
 */

data class InspectionTime(var id: Int = 0,
                          var startTime: Long? = null,
                          var endTime: Long? = null) : Parcelable {

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        startTime = parcel.readValue(Long::class.java.classLoader) as Long?
        endTime = parcel.readValue(Long::class.java.classLoader) as Long?
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeValue(startTime)
        parcel.writeValue(endTime)
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
