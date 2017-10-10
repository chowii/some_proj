package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable
import com.soho.sohoapp.network.Keys
import com.soho.sohoapp.utils.orFalse
import java.util.*
import kotlin.collections.HashSet

/**
 * Created by chowii on 25/7/17.
 */

class User : BasicUser {

    var authenticationToken: String? = null
    var country: String? = null
    var verifications: List<Verification>? = null
    var intentions: HashSet<String> = HashSet()
    var role: String = ROLE.USER().const
    var agentsLicenseNumber: String? = null

    constructor() : super()

    constructor(parcel: Parcel) : super(parcel) {
        authenticationToken = parcel.readString()
        country = parcel.readString()
        parcel.readTypedList(verifications, Verification.CREATOR)
        parcel.readStringArray(intentions.toTypedArray())
        role = parcel.readString()
        agentsLicenseNumber = parcel.readString()
    }

    fun getFullnameShort() =
            if (firstName != null && lastName != null) String.format(Locale.getDefault(), "%s.%s", firstName?.get(0), lastName) else ""

    fun getFullname() = String.format(Locale.getDefault(), "%s %s", firstName, lastName)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(authenticationToken)
        parcel.writeString(country)
        parcel.writeTypedList(verifications)
        parcel.writeStringArray(intentions.toTypedArray())
        parcel.writeString(role)
        parcel.writeString(agentsLicenseNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

sealed class ROLE(val const: String) {
    class AGENT : ROLE(Keys.Role.AGENT)
    class USER : ROLE(Keys.Role.USER)
}

sealed class INTENTION(val const: String) {
    class BUYING : INTENTION(Keys.Intention.BUYING)
    class RENTING : INTENTION(Keys.Intention.RENTING)
    class SELLING : INTENTION(Keys.Intention.SELLING)
}

fun User.isSelling(): Boolean = this.intentions.contains(INTENTION.SELLING().const)
fun User.isRenting(): Boolean = this.intentions.contains(INTENTION.RENTING().const)
fun User.isBuying(): Boolean = this.intentions.contains(INTENTION.BUYING().const)
fun User.isAgent(): Boolean = this.role?.contains(ROLE.AGENT().const).orFalse()