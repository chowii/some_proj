package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable

class Verification() : Parcelable {

    var id: Int = 0
    var type: String? = null
    var text: String? = null
    var state: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        type = parcel.readString()
        text = parcel.readString()
        state = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(type)
        parcel.writeString(text)
        parcel.writeString(state)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Verification> {
        override fun createFromParcel(parcel: Parcel): Verification {
            return Verification(parcel)
        }

        override fun newArray(size: Int): Array<Verification?> {
            return arrayOfNulls(size)
        }
    }

}
