package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Jovan on 15/9/17.
 */

open class BasicUser() : Parcelable {

    var email: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var dateOfBirth: Long? = null
    var avatar: Image? = null
    var isProfileComplete: Boolean = false

    constructor(parcel: Parcel) : this() {
        email = parcel.readString()
        firstName = parcel.readString()
        lastName = parcel.readString()
        dateOfBirth = parcel.readValue(Long::class.java.classLoader) as Long?
        avatar = parcel.readParcelable(Image::class.java.classLoader)
        isProfileComplete = parcel.readInt() == 1

    }

    fun fullName(): String {
        return "$firstName $lastName"
    }

    fun fullNameWithAbbreviatedFirstName(): String {
        var abbreviatedFirstName = ""
        if ((firstName?.length ?: 0) > 0) {
            abbreviatedFirstName = firstName?.get(0).toString() ?: ""
            abbreviatedFirstName += ". "
        }
        return "$abbreviatedFirstName$lastName"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeValue(dateOfBirth)
        parcel.writeParcelable(avatar, flags)
        parcel.writeInt(if (isProfileComplete) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BasicUser> {
        override fun createFromParcel(parcel: Parcel): BasicUser {
            return BasicUser(parcel)
        }

        override fun newArray(size: Int): Array<BasicUser?> {
            return arrayOfNulls(size)
        }
    }

}