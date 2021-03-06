package com.soho.sohoapp.data.models

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.soho.sohoapp.data.listdata.Displayable
import java.util.*

/**
 * Created by Jovan on 9/10/17.
 */

data class PropertyFile(var id: Int? = null,
                        var name: String = "",
                        var filePhoto: String? = null,
                        var documentType: String? = null,
                        var isCost: Boolean? = null,
                        var amount: Float? = null,
                        var updatedAt: Date? = null,
                        var fileToUploadUri: Uri? = null) : Parcelable, Displayable {

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        filePhoto = parcel.readString()
        documentType = parcel.readString()
        isCost = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        amount = parcel.readValue(Float::class.java.classLoader) as? Float
    }

    fun clone(from: PropertyFile) = this.apply {
        id = from.id
        name = from.name
        filePhoto = from.filePhoto
        documentType = from.documentType
        isCost = from.isCost
        amount = from.amount
        updatedAt = from.updatedAt
        fileToUploadUri = from.fileToUploadUri
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(filePhoto)
        parcel.writeString(documentType)
        parcel.writeValue(isCost)
        parcel.writeValue(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PropertyFile> {
        override fun createFromParcel(parcel: Parcel): PropertyFile {
            return PropertyFile(parcel)
        }

        override fun newArray(size: Int): Array<PropertyFile?> {
            return arrayOfNulls(size)
        }
    }

}