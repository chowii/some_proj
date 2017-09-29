package com.soho.sohoapp.feature.marketplaceview.feature.filters.savedfilters

import android.support.annotation.StringRes
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs
import com.soho.sohoapp.feature.BaseViewInteractable

interface SavedFiltersContract {
    interface ViewPresentable {
        fun onDeleteFilter(filter: MarketplaceFilterWithSuburbs)
    }

    interface ViewInteractable : BaseViewInteractable {
        fun setPresentable(presentable: ViewPresentable)
        fun updateDataSet(dataSet: List<MarketplaceFilterWithSuburbs>)
        fun toggleLoadingIndicator(loading: Boolean)
        fun showMessage(@StringRes stringRes:Int)
    }
}
