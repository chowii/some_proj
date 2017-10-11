package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.soho.sohoapp.R
import com.soho.sohoapp.data.enums.VerificationStatus
import com.soho.sohoapp.data.enums.VerificationType

class Verification() : Parcelable {

    var id: Int = 0
    var type: String? = null
    var text: String? = null
    var state: String? = null
    var attachments: List<Attachment> = mutableListOf()

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        type = parcel.readString()
        text = parcel.readString()
        state = parcel.readString()
        attachments = parcel.createTypedArrayList(Attachment)
    }

    @StringRes
    fun getStateLabel() = when (this.state) {
        VerificationStatus.NEW -> R.string.verification_unverified
        VerificationStatus.PENDING -> R.string.verification_pending
        VerificationStatus.NOT_VERIFIED -> R.string.verification_rejected
        VerificationStatus.VERIFIED -> R.string.verification_verified
        else -> R.string.verification_unknown
    }

    @StringRes
    fun getDisplayableType() = when (this.type) {
        VerificationType.LICENCE -> R.string.settings_account_verification_photo_id_text
        VerificationType.MOBILE_NUMBER -> R.string.settings_account_verification_mobile_number_text
        VerificationType.AGENT_LICENCE -> R.string.settings_account_verification_agent_license_text
        else -> R.string.verification_unknown
    }

    @ColorRes
    fun getColor() = when (this.state) {
        VerificationStatus.NEW, VerificationStatus.PENDING -> R.color.propertyStateNotCompleted
        VerificationStatus.NOT_VERIFIED -> R.color.propertyStateNotVerified
        VerificationStatus.VERIFIED -> R.color.propertyStateVerified
        else -> R.color.white
    }

    @DrawableRes
    fun getIcon() = when (this.type) {
        VerificationType.LICENCE -> R.drawable.drivers_card
        VerificationType.MOBILE_NUMBER -> R.drawable.touchscreen_smartphone
        VerificationType.AGENT_LICENCE -> R.drawable.license
        else -> 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(type)
        parcel.writeString(text)
        parcel.writeString(state)
        parcel.writeTypedList(attachments)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Verification> {
        override fun createFromParcel(parcel: Parcel): Verification {
            return Verification(parcel)
        }

        override fun newArray(size: Int): Array<Verification?> {
            return arrayOfNulls(size)
        }
    }

}
