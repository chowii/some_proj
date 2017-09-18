package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable

class Location() : Parcelable {

    var suburb: String? = null
    var state: String? = null
    var postcode: String? = null
    var country: String? = null
    var latitude: Double? = null
    var longitude: Double? = null
    var fullAddress: String? = null
    var addressLine1: String? = null
    var addressLine2: String? = null
    var maskAddress: Boolean = true

    constructor(parcel: Parcel) : this() {
        suburb = parcel.readString()
        state = parcel.readString()
        postcode = parcel.readString()
        country = parcel.readString()
        latitude = parcel.readValue(Double::class.java.classLoader) as? Double
        longitude = parcel.readValue(Double::class.java.classLoader) as? Double
        fullAddress = parcel.readString()
        addressLine1 = parcel.readString()
        addressLine2 = parcel.readString()
        maskAddress = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(suburb)
        parcel.writeString(state)
        parcel.writeString(postcode)
        parcel.writeString(country)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
        parcel.writeString(fullAddress)
        parcel.writeString(addressLine1)
        parcel.writeString(addressLine2)
        parcel.writeByte(if (maskAddress) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }

}
