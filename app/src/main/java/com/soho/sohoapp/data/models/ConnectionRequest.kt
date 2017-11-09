package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable
import com.soho.sohoapp.data.listdata.Displayable

data class ConnectionRequest(var id: Int? = null,
                             var role: String? = null,
                             var userDetails: BasicUser? = null,
                             var state: String? = null,
                             var propertyId: Int? = null,
                             var updatedAt: Long? = null) : Parcelable, Displayable {

    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readParcelable<BasicUser>(BasicUser::class.java.classLoader),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Long::class.java.classLoader) as Long?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(role)
        writeParcelable(userDetails, 0)
        writeString(state)
        writeValue(propertyId)
        writeValue(updatedAt)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ConnectionRequest> = object : Parcelable.Creator<ConnectionRequest> {
            override fun createFromParcel(source: Parcel): ConnectionRequest = ConnectionRequest(source)
            override fun newArray(size: Int): Array<ConnectionRequest?> = arrayOfNulls(size)
        }
    }
}