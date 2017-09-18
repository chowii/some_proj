package com.soho.sohoapp.feature.home.more.model

import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 12/9/17.
 */
class SettingItem(val title: String?, val dateOfBirth: Long, val iconRes: Int): BaseModel {
    override fun getItemViewType(): Int = R.layout.item_settings
}