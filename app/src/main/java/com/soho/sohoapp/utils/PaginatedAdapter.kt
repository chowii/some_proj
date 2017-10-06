package com.soho.sohoapp.utils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soho.sohoapp.R
import com.soho.sohoapp.data.models.PaginationInformation

open class PaginatedAdapter<T: Any>(val paginatedAdapterListener: PaginatedAdapterListener<T>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_TYPE_PAGINATION_FOOTER = 0
    private val ITEM_TYPE_DATA_SET = 1

    var dataSet:List<T> = ArrayList()
    private var paginationInformation: PaginationInformation? = null

    private val shouldShowPaginationFooter
        get() = paginationInformation?.hasNextPage == true

    // MARK: - ================== General methods ==================

    open fun replaceDataset(dataSet: List<T>, paginationInformation: PaginationInformation?) {
        this.dataSet = dataSet
        this.paginationInformation = paginationInformation
        notifyDataSetChanged()
    }

    open fun addAdditionalItems(additionalItems: List<T>, paginationInformation: PaginationInformation?) {
        this.dataSet += additionalItems
        this.paginationInformation = paginationInformation
        notifyDataSetChanged()
    }

    // MARK: - ================== RecyclerView.Adapter related methods ==================

    override fun getItemCount(): Int =
            dataSet.size + if(shouldShowPaginationFooter) 1 else 0

    override fun getItemViewType(position: Int): Int =
            if(position >= dataSet.size && shouldShowPaginationFooter) ITEM_TYPE_PAGINATION_FOOTER
            else ITEM_TYPE_DATA_SET

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder is PaginatedFooterViewHolder && paginationInformation?.nextPage != null) {
            paginationInformation?.nextPage?.let { paginatedAdapterListener.shouldLoadNextPage(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                ITEM_TYPE_PAGINATION_FOOTER -> PaginatedFooterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pagination_footer, parent, false))
                else -> viewHolderFormItem(parent, viewType)
            }

    // MARK: - ================== Methods to be overidden ==================

    open fun viewHolderFormItem(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        throw Exception(parent.context.getString(R.string.exception_paginated_adapter_override))
    }

    // MARK: - ================== ViewHolder ==================

    class PaginatedFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

interface PaginatedAdapterListener<in T:Any> {
    fun adapterItemClicked(item: T)
    fun shouldLoadNextPage(page: Int)
}