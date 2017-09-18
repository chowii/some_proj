package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable

class Photo(): Parcelable {

    var image: Image? = null

    constructor(parcel: Parcel) : this() {
        image = parcel.readParcelable(Image::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(image, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }

}

