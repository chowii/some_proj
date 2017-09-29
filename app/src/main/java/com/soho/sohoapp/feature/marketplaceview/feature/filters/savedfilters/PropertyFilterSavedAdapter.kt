package com.soho.sohoapp.feature.marketplaceview.feature.filters.savedfilters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.soho.sohoapp.R
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs

/**
 * Created by chowii on 25/8/17.
 */
class PropertyFilterSavedAdapter(val onClickListener : PropertyFilterOnClickListener) :
        RecyclerView.Adapter<PropertyFilterSavedAdapter.SavedItemViewHolder>() {

    private lateinit var filters : List<MarketplaceFilterWithSuburbs>

    // MARK: - ================== RecyclerView.Adapter related ==================

    override fun getItemCount(): Int = filters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            SavedItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter_saved, parent, false))

    override fun onBindViewHolder(holder: SavedItemViewHolder?, position: Int) {
        holder?.bind(filters[position])
        holder?.itemView?.setOnClickListener {
            onClickListener.filterSelected(filters[position])
        }
    }

    // MARK: - ================== Additional methods ==================

    fun updateDataSet(newDataSet:List<MarketplaceFilterWithSuburbs>) {
        this.filters = newDataSet
        notifyDataSetChanged()
    }

    fun getFilterAt(index:Int) : MarketplaceFilterWithSuburbs =
            filters[index]

    // MARK: - ================== ViewHolder ==================

    class SavedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.subtitle_text_view)
        lateinit var subtitleTextView : TextView

        @BindView(R.id.title_text_view)
        lateinit var titleTextView : TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(filter:MarketplaceFilterWithSuburbs) {
            titleTextView.text = filter.suburbsDisplayString(
                    itemView.context.getString(R.string.filters_multiple_suburbs_format),
                    itemView.context.getString(R.string.filters_all_suburbs))
            subtitleTextView.text = filter.marketplaceFilter.priceRangeDisplayString(
                    itemView.context.getString(R.string.filters_search_bar_display_format),
                    itemView.context.getString(R.string.dollar_format),
                    itemView.context.getString(R.string.filters_price_any))
        }
    }

    interface PropertyFilterOnClickListener {
        fun filterSelected(filter:MarketplaceFilterWithSuburbs)
    }
}
