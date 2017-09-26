package com.soho.sohoapp.feature.home.more.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soho.sohoapp.BaseViewHolder
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.home.BaseModel
import com.soho.sohoapp.feature.home.more.viewholder.MoreViewHolder

/**
 * Created by chowii on 10/09/17.
 */
class MoreAdapter(private val moreList: List<BaseModel>, private val moreListener: MoreViewHolder.OnMoreItemClickListener)
    : RecyclerView.Adapter<BaseViewHolder<BaseModel>>() {


    override fun getItemCount(): Int = moreList.size

    override fun getItemViewType(position: Int): Int {
        return moreList[position].itemViewType
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<BaseModel> {
        val itemView: View = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)
        when (viewType) {
            R.layout.item_more -> return MoreViewHolder(itemView, moreListener) as BaseViewHolder<BaseModel>
        }
        return MoreViewHolder(itemView, moreListener) as BaseViewHolder<BaseModel>
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseModel>?, position: Int) {
        holder?.onBindViewHolder(moreList[holder.adapterPosition])
    }
}