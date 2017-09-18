package com.soho.sohoapp.feature.home.more.model

import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 10/09/17.
 */
class MoreItem(val moreItem: String, val moreDrawable: Int) : BaseModel {

    override fun getItemViewType(): Int = R.layout.item_more


}