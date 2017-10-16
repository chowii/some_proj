package com.soho.sohoapp.data.models

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

class Image() : Parcelable {

    var holder: Int = 0
    var drawableId: Int = 0

    var imageUrl: String? = null
    var smallImageUrl: String? = null
    var mediumImageUrl: String? = null
    var largeImageUrl: String? = null

    var filePath: String? = null
    var uri: Uri? = null

    constructor(drawableId: Int) : this() {
        this.drawableId = drawableId
    }

    constructor(parcel: Parcel) : this() {
        holder = parcel.readInt()
        drawableId = parcel.readInt()
        imageUrl = parcel.readString()
        smallImageUrl = parcel.readString()
        mediumImageUrl = parcel.readString()
        largeImageUrl = parcel.readString()
        filePath = parcel.readString()
        uri = parcel.readParcelable(Uri::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(holder)
        parcel.writeInt(drawableId)
        parcel.writeString(imageUrl)
        parcel.writeString(smallImageUrl)
        parcel.writeString(mediumImageUrl)
        parcel.writeString(largeImageUrl)
        parcel.writeString(filePath)
        parcel.writeParcelable(uri, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }

}