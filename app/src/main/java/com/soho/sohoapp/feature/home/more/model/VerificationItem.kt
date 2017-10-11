package com.soho.sohoapp.feature.home.more.model

import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.Verification
import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 11/9/17.
 */
class VerificationItem(var verification: Verification) : BaseModel {

    override fun getItemViewType(): Int = R.layout.item_verification

}