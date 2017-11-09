package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.StringRes
import com.soho.sohoapp.R
import com.soho.sohoapp.data.enums.PropertyUserRole
import com.soho.sohoapp.data.listdata.Displayable
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole

/**
 * Created by Jovan on 15/9/17.
 */

class PropertyUser() : Parcelable, Displayable {

    var id: Int? = null
    var role: String? = null
    var userDetails: BasicUser? = null
    var lastMessage: String? = null
    var lastMessageAt: Long? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        role = parcel.readString()
        userDetails = parcel.readParcelable(BasicUser::class.java.classLoader)
        lastMessage = parcel.readString()
        lastMessageAt = parcel.readValue(Long::class.java.classLoader) as Long?
    }

    fun representingUserFullName(): String {
        return (if (role.equals(PropertyRole.AGENT, true)) userDetails?.fullName() else userDetails?.fullNameWithAbbreviatedFirstName()) ?: ""
    }

    @StringRes
    fun getDisplayableRole() = when (role) {
        PropertyUserRole.OWNER -> R.string.connection_confirm_role_owner
        PropertyUserRole.AGENT -> R.string.connection_confirm_role_agent
        else -> R.string.connection_confirm_role_guest
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(role)
        parcel.writeParcelable(userDetails, flags)
        parcel.writeString(lastMessage)
        parcel.writeValue(lastMessageAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PropertyUser> {
        override fun createFromParcel(parcel: Parcel): PropertyUser {
            return PropertyUser(parcel)
        }

        override fun newArray(size: Int): Array<PropertyUser?> {
            return arrayOfNulls(size)
        }
    }

}