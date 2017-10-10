package com.soho.sohoapp.feature.home.more.model

import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 11/9/17.
 */
class VerificationItem(var verificationId: Int,
                       var type: AccountVerificationType,
                       var state: AccountVerificationStatus) : BaseModel {

    override fun getItemViewType(): Int = R.layout.item_verification

}