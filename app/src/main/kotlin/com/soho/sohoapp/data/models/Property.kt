package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable

class Property : BasicProperty {

    var landSize: Int = 0
    var landSizeMeasurement: String? = null
    var auctionDate: Long = 0
    var rennovationDetails: String? = null
    var agentLicenseNumber: String? = null
    var agentMobileNumber: String? = null
    var propertyListing: PropertyListing? = null
    var propertyFinance: PropertyFinance? = null
    var verifications: List<Verification>? = null

    constructor() : super()

    constructor(parcel: Parcel) : super(parcel) {
        landSize = parcel.readInt()
        landSizeMeasurement = parcel.readString()
        auctionDate = parcel.readLong()
        rennovationDetails = parcel.readString()
        agentLicenseNumber = parcel.readString()
        agentMobileNumber = parcel.readString()
        propertyListing = parcel.readParcelable(PropertyListing::class.java.classLoader)
        propertyFinance = parcel.readParcelable(PropertyFinance::class.java.classLoader)
        verifications = parcel.createTypedArrayList(Verification)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeInt(landSize)
        parcel.writeString(landSizeMeasurement)
        parcel.writeLong(auctionDate)
        parcel.writeString(rennovationDetails)
        parcel.writeString(agentLicenseNumber)
        parcel.writeString(agentMobileNumber)
        parcel.writeParcelable(propertyListing, flags)
        parcel.writeParcelable(propertyFinance, flags)
        parcel.writeTypedList(verifications)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Property> {
        override fun createFromParcel(parcel: Parcel): Property {
            return Property(parcel)
        }

        override fun newArray(size: Int): Array<Property?> {
            return arrayOfNulls(size)
        }
    }

}
