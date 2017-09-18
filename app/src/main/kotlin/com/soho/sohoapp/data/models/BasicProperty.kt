package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType

/**
 * Created by Jovan on 15/9/17.
 */

open class BasicProperty() : BaseModel, Parcelable {

    var id: Int = 0
    var state: String? = null
    var title: String? = null
    var description: String? = null
    var type: String? = null
    var isInvestment: Boolean = false
    var isFavourite: Boolean = false
    var rentPrice: Int = 0
    var salePrice: Int = 0
    var updatedAt: Long = 0
    var bedrooms: Int = 0
    var bathrooms: Int = 0
    var carspots: Int = 0
    var location: Location? = null
    var photos: List<Photo>? = null
    var agentLogo: Image? = null
    var propertyUsers: List<PropertyUser>? = null

    //Convenience Getters
    val owner:PropertyUser?
        get() = getFirstPropertyUserForRole(PropertyRole.OWNER)
    val agent:PropertyUser?
        get() = getFirstPropertyUserForRole(PropertyRole.AGENT)

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        state = parcel.readString()
        title = parcel.readString()
        description = parcel.readString()
        type = parcel.readString()
        isInvestment = parcel.readByte() != 0.toByte()
        isFavourite = parcel.readByte() != 0.toByte()
        rentPrice = parcel.readInt()
        salePrice = parcel.readInt()
        updatedAt = parcel.readLong()
        bedrooms = parcel.readInt()
        bathrooms = parcel.readInt()
        carspots = parcel.readInt()
        location = parcel.readParcelable(Location::class.java.classLoader)
        photos = parcel.createTypedArrayList(Photo)
        agentLogo = parcel.readParcelable(Image::class.java.classLoader)
        propertyUsers = parcel.createTypedArrayList(PropertyUser)
    }

    override fun getItemViewType(): Int {
        return R.layout.item_property;
    }

    @NonNull
    //Gets nested image in photos as array
    fun getPhotosAsImages(): List<Image> {
        val images =  photos?.filter({ it != null })?.map({ it.image!! }) ?: arrayListOf()
        return if (images.size > 0) images else arrayListOf(Image(PropertyType.getDefaultImage(type)))
    }

    @Nullable
    //Gets representing user of property, agent > owner, can return null
    fun getRepresentingUser(): PropertyUser? {
        agent?.let { user ->
            return user
        }
        owner?.let { user ->
            return user
        }
        return null
    }

    @Nullable
    //Gets First user of specified role, can return null.
    fun getFirstPropertyUserForRole(role:String): PropertyUser? {
        val filteredUsers = propertyUsers?.filter({ it.role.equals(role, true) })
        if(filteredUsers != null && filteredUsers.size > 0) {
            return filteredUsers.first()
        }
        return null
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(state)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(type)
        parcel.writeByte(if (isInvestment) 1 else 0)
        parcel.writeByte(if (isFavourite) 1 else 0)
        parcel.writeInt(rentPrice)
        parcel.writeInt(salePrice)
        parcel.writeLong(updatedAt)
        parcel.writeInt(bedrooms)
        parcel.writeInt(bathrooms)
        parcel.writeInt(carspots)
        parcel.writeParcelable(location, flags)
        parcel.writeTypedList(photos)
        parcel.writeParcelable(agentLogo, flags)
        parcel.writeTypedList(propertyUsers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BasicProperty> {
        override fun createFromParcel(parcel: Parcel): BasicProperty {
            return BasicProperty(parcel)
        }

        override fun newArray(size: Int): Array<BasicProperty?> {
            return arrayOfNulls(size)
        }
    }

}


