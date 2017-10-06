package com.soho.sohoapp.feature.marketplaceview.components

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup

import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.BasicProperty
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.utils.PaginatedAdapter
import com.soho.sohoapp.utils.PaginatedAdapterListener

/**
 * Created by chowii on 14/8/17.
 */

class MarketPlaceAdapter(paginatedAdapterListener: PaginatedAdapterListener<BasicProperty>)
    : PaginatedAdapter<BasicProperty>(paginatedAdapterListener) {

    override fun viewHolderFormItem(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_property, parent, false)
        return PropertyViewHolder(itemView, paginatedAdapterListener)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        super.onBindViewHolder(holder, position)
        (holder as? PropertyViewHolder)?.onBindViewHolder(dataSet[position])
    }
}
