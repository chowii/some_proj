package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable
import com.soho.sohoapp.data.enums.PropertyStatus

class PropertyListing() : Parcelable {

    var id: Int = 0
    var state: String? = null
    var canReceiveSalesOffers: Boolean = false
    var canReceiveRentOffers: Boolean = false
    var saleTitle: String? = null
    var rentTitle: String? = null
    var auctionTitle: String? = null
    var discoverableTitle: String? = null
    var isOnSiteAuction: Boolean = false
    var auctionTime: Long? = null
    var rentPaymentFrequency: String? = null
    var availableFrom: Long? = null
    var isAppointmentOnly: Boolean = false
    var offSiteLocation: Location? = null
    var inspectionTimes: List<InspectionTime>? = null

    val isPublic: Boolean
        get() = PropertyStatus.DISCOVERABLE == state ||
                PropertyStatus.SALE == state ||
                PropertyStatus.AUCTION == state ||
                PropertyStatus.RENT == state

    val isPrivate: Boolean
        get() = PropertyStatus.PRIVATE == state

    val isArchived: Boolean
        get() = PropertyStatus.ARCHIVED == state || PropertyStatus.SOLD == state

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        state = parcel.readString()
        canReceiveSalesOffers = parcel.readByte() != 0.toByte()
        canReceiveRentOffers = parcel.readByte() != 0.toByte()
        saleTitle = parcel.readString()
        rentTitle = parcel.readString()
        auctionTitle = parcel.readString()
        discoverableTitle = parcel.readString()
        isOnSiteAuction = parcel.readByte() != 0.toByte()
        auctionTime = parcel.readValue(Long::class.java.classLoader) as Long?
        rentPaymentFrequency = parcel.readString()
        availableFrom = parcel.readValue(Long::class.java.classLoader) as Long?
        isAppointmentOnly = parcel.readByte() != 0.toByte()
        offSiteLocation = parcel.readParcelable(Location::class.java.classLoader)
        inspectionTimes = parcel.createTypedArrayList(InspectionTime)
    }

    fun getInspectionTimesSafe() = inspectionTimes ?: ArrayList<InspectionTime>()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(state)
        parcel.writeByte(if (canReceiveSalesOffers) 1 else 0)
        parcel.writeByte(if (canReceiveRentOffers) 1 else 0)
        parcel.writeString(saleTitle)
        parcel.writeString(rentTitle)
        parcel.writeString(auctionTitle)
        parcel.writeString(discoverableTitle)
        parcel.writeByte(if (isOnSiteAuction) 1 else 0)
        parcel.writeValue(auctionTime)
        parcel.writeString(rentPaymentFrequency)
        parcel.writeValue(availableFrom)
        parcel.writeByte(if (isAppointmentOnly) 1 else 0)
        parcel.writeParcelable(offSiteLocation, flags)
        parcel.writeTypedList(inspectionTimes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PropertyListing> {
        override fun createFromParcel(parcel: Parcel): PropertyListing {
            return PropertyListing(parcel)
        }

        override fun newArray(size: Int): Array<PropertyListing?> {
            return arrayOfNulls(size)
        }
    }


}
