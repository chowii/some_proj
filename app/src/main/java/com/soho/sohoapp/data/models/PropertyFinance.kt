package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable

class PropertyFinance() : Parcelable {

    var id: Int = 0
    var purchasePrice: Double = 0.0
    var loanAmount: Double = 0.0
    var estimatedValue: Double = 0.0
    var isRented: Boolean = false
    var actualRent: Double = 0.0
    var estimatedRent: Double = 0.0
    var leasedToDate: Long? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        purchasePrice = parcel.readDouble()
        loanAmount = parcel.readDouble()
        estimatedValue = parcel.readDouble()
        isRented = parcel.readByte() != 0.toByte()
        actualRent = parcel.readDouble()
        estimatedRent = parcel.readDouble()
        leasedToDate = parcel.readValue(Long::class.java.classLoader) as Long?
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeDouble(purchasePrice)
        parcel.writeDouble(loanAmount)
        parcel.writeDouble(estimatedValue)
        parcel.writeByte(if (isRented) 1 else 0)
        parcel.writeDouble(actualRent)
        parcel.writeDouble(estimatedRent)
        parcel.writeValue(leasedToDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PropertyFinance> {
        override fun createFromParcel(parcel: Parcel): PropertyFinance {
            return PropertyFinance(parcel)
        }

        override fun newArray(size: Int): Array<PropertyFinance?> {
            return arrayOfNulls(size)
        }
    }

}
