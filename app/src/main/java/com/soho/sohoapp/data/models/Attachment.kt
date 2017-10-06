package com.soho.sohoapp.data.models

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.soho.sohoapp.data.listdata.Displayable

class Attachment() : Parcelable, Displayable {

    var id: Int = 0
    var fileUrl: String? = null
    var uri: Uri? = null
    var holder: Int = 0
    var filePath: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        fileUrl = parcel.readString()
        uri = parcel.readParcelable(Uri::class.java.classLoader)
        holder = parcel.readInt()
        filePath = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(fileUrl)
        parcel.writeParcelable(uri, flags)
        parcel.writeInt(holder)
        parcel.writeString(filePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Attachment> {
        override fun createFromParcel(parcel: Parcel): Attachment {
            return Attachment(parcel)
        }

        override fun newArray(size: Int): Array<Attachment?> {
            return arrayOfNulls(size)
        }
    }
}