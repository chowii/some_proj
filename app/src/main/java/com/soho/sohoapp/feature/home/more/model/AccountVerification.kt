package com.soho.sohoapp.feature.home.more.model

import android.support.annotation.ColorInt
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.more.model.AccountVerificationStatus.*

/**
 * Created by chowii on 12/9/17.
 */
class AccountVerification(
        override var id: Int,
        override var type: AccountVerificationType,
        override var text: Any,
        override var state: AccountVerificationStatusTitle
) : UserVerifiable


interface UserVerifiable {

    var id: Int
    var type: AccountVerificationType
    var text: Any
    var state: AccountVerificationStatusTitle

}

enum class AccountVerificationStatusTitle {
    new{
        override fun type(): AccountVerificationStatus = NEW
    },
    pending{
        override fun type(): AccountVerificationStatus = PENDING
    },
    not_verified{
        override fun type(): AccountVerificationStatus = NOT_VERIFIED
    },
    verified{
        override fun type(): AccountVerificationStatus = VERIFIED
    };

    abstract fun type(): AccountVerificationStatus
}

enum class AccountVerificationStatus(@ColorInt val colorRes: Int) {

    NEW(R.color.orangePrimary),
    PENDING(R.color.orangePrimary),
    NOT_VERIFIED(R.color.redPrimary),
    VERIFIED(R.color.colorPrimary),

}

enum class AccountVerificationType(val type: String, val res: Int) {
    LicenceVerification("Photo Id", R.drawable.drivers_card),
    MobileVerification("Mobile Number", R.drawable.touchscreen_smartphone),
    AgentLicenceVerification("Agent License", R.drawable.license),
}