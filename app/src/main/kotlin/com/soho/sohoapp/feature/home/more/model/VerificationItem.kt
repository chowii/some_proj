package com.soho.sohoapp.feature.home.more.model

import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 11/9/17.
 */
class VerificationItem(var type: AccountVerificationType, var state: AccountVerificationStatus): BaseModel {

    var settingSubtitle: String = ""

    override fun getItemViewType(): Int = R.layout.item_verification

}