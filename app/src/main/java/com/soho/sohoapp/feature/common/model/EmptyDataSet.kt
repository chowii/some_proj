package com.soho.sohoapp.feature.common.model

import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel

/**
 * Created by chowii on 29/12/17.
 */
class EmptyDataSet(val header: String, val subHeader: String, val buttonText: String) : BaseModel {

    override fun getItemViewType() = R.layout.item_empty_dataset

}