package com.soho.sohoapp.feature.home.more.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soho.sohoapp.BaseViewHolder
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.more.viewholder.SettingViewHolder
import com.soho.sohoapp.feature.home.more.viewholder.VerificationViewHolder
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.filterviewholder.HeaderViewHolder

/**
 * Created by chowii on 11/9/17.
 */

class SettingsAdapter(private val list: List<BaseModel>, private val moreListener: OnSettingsItemClickListener): RecyclerView.Adapter<BaseViewHolder<BaseModel>>() {


    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int) = list[position].itemViewType

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<BaseModel> {
        val itemView: View = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)
        when(viewType){
            R.layout.item_filter_header -> return HeaderViewHolder(itemView) as BaseViewHolder<BaseModel>
            R.layout.item_settings -> return SettingViewHolder(itemView, moreListener) as BaseViewHolder<BaseModel>
            R.layout.item_verification -> return VerificationViewHolder(itemView, moreListener, itemView.context) as BaseViewHolder<BaseModel>
        }
        return VerificationViewHolder(itemView, moreListener, itemView.context) as BaseViewHolder<BaseModel>
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseModel>?, position: Int) {
        holder?.onBindViewHolder(list[holder.adapterPosition])
    }

    interface OnSettingsItemClickListener {
        fun onSettingsItemClicked(item: String)
    }

}