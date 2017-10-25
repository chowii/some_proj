package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable

class Property : BasicProperty {

    var landSize: Int = 0
    var landSizeMeasurement: String? = null
    var auctionDate: Long? = null
    var renovationDetails: String? = null
    var agentLicenseNumber: String? = null
    var agentMobileNumber: String? = null
    var propertyListing: PropertyListing? = null
    var propertyFinance: PropertyFinance? = null
    var verifications: MutableList<Verification>? = null
    var files: List<PropertyFile> = mutableListOf()

    constructor() : super()

    constructor(parcel: Parcel) : super(parcel) {
        landSize = parcel.readInt()
        landSizeMeasurement = parcel.readString()
        auctionDate = parcel.readValue(Long::class.java.classLoader) as Long?
        renovationDetails = parcel.readString()
        agentLicenseNumber = parcel.readString()
        agentMobileNumber = parcel.readString()
        propertyListing = parcel.readParcelable(PropertyListing::class.java.classLoader)
        propertyFinance = parcel.readParcelable(PropertyFinance::class.java.classLoader)
        verifications = parcel.createTypedArrayList(Verification)
        files = parcel.createTypedArrayList(PropertyFile)
    }

    fun getPropertyListingSafe() = propertyListing ?: PropertyListing()

    fun getPropertyFinanceSafe() = propertyFinance ?: PropertyFinance()

    fun getLocationSafe() = location ?: Location()

    fun getVerificationsSafe() = verifications ?: ArrayList()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeInt(landSize)
        parcel.writeString(landSizeMeasurement)
        parcel.writeValue(auctionDate)
        parcel.writeString(renovationDetails)
        parcel.writeString(agentLicenseNumber)
        parcel.writeString(agentMobileNumber)
        parcel.writeParcelable(propertyListing, flags)
        parcel.writeParcelable(propertyFinance, flags)
        parcel.writeTypedList(verifications)
        parcel.writeTypedList(files)
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
