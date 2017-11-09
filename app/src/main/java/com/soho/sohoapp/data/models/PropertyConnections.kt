package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable

data class PropertyConnections(var users: List<PropertyUser> = mutableListOf(),
                               var requests: List<ConnectionRequest> = mutableListOf()) : Parcelable {

    constructor(source: Parcel) : this(
            source.createTypedArrayList(PropertyUser.CREATOR),
            source.createTypedArrayList(ConnectionRequest.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(users)
        writeTypedList(requests)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PropertyConnections> = object : Parcelable.Creator<PropertyConnections> {
            override fun createFromParcel(source: Parcel): PropertyConnections = PropertyConnections(source)
            override fun newArray(size: Int): Array<PropertyConnections?> = arrayOfNulls(size)
        }
    }
}