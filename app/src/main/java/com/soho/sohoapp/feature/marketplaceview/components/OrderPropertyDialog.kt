package com.soho.sohoapp.feature.marketplaceview.components

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import com.soho.sohoapp.R
import com.soho.sohoapp.feature.marketplaceview.components.OrderPropertyDialog.FilterType.*
import com.soho.sohoapp.network.Keys
import com.soho.sohoapp.network.Keys.OrderDirection.ASCENDING
import com.soho.sohoapp.network.Keys.OrderDirection.DESCENDING
import java.util.*

/**
 * Created by mariolopez on 14/11/17.
 */
class OrderPropertyDialog(private val context: Context) {

    private var newestToOldest: String = context.getString(R.string.market_order_newest_to_oldest)
    private var oldestToNewest: String = context.getString(R.string.market_order_oldest_to_newest)
    private var highToLow: String = context.getString(R.string.market_order_high_to_low)
    private var lowToHigh: String = context.getString(R.string.market_order_low_to_high)

    fun show(listener: OnMarketPlaceOrderSelected): AlertDialog {
        val builder = AlertDialog.Builder(context)

        val arrayAdapter = ArrayAdapter<String>(context, R.layout.dialog_item)
        val itemList = createItems()
        arrayAdapter.addAll(itemList)

        builder.setTitle(R.string.market_order_order_by_title)
        builder.setAdapter(arrayAdapter) { _, which ->
            val selectedItem = itemList[which]
            when (selectedItem) {
                newestToOldest -> listener.onOrderFilterSelected(NewToOld)
                oldestToNewest -> listener.onOrderFilterSelected(OldToNew)//if it's rent or sale is irrelevant here
                highToLow -> listener.onOrderFilterSelected(HighToLowRent)
                lowToHigh -> listener.onOrderFilterSelected(LowToHighRent)

            }
        }
        return builder.show()
    }

    private fun createItems(): List<String> {
        val listItems = ArrayList<String>()
        //if it's rent or sale is irrelevant here
        listItems.add(context.getString(HighToLowRent.stringRes))
        listItems.add(context.getString(LowToHighRent.stringRes))
        listItems.add(context.getString(NewToOld.stringRes))
        listItems.add(context.getString(OldToNew.stringRes))
        return listItems
    }

    sealed class FilterType(@StringRes val stringRes: Int, val orderType: String, val orderDirectio: String) {
        object HighToLowRent : FilterType(R.string.market_order_high_to_low, Keys.OrderBy.RENT, DESCENDING)
        object HighToLowSale : FilterType(R.string.market_order_high_to_low, Keys.OrderBy.SALE_VALUE, DESCENDING)
        object LowToHighRent : FilterType(R.string.market_order_low_to_high, Keys.OrderBy.RENT, ASCENDING)
        object LowToHighSale : FilterType(R.string.market_order_low_to_high, Keys.OrderBy.SALE_VALUE, ASCENDING)
        object OldToNew : FilterType(R.string.market_order_oldest_to_newest, Keys.OrderBy.LISTED_AT, ASCENDING)
        object NewToOld : FilterType(R.string.market_order_newest_to_oldest, Keys.OrderBy.LISTED_AT, DESCENDING)

    }
    interface OnMarketPlaceOrderSelected {
        fun onOrderFilterSelected(type: FilterType)
    }
    companion object {
        @JvmStatic
        fun getSealedClass(orderType: String, orderDirection: String): OrderPropertyDialog.FilterType? {
            when (orderDirection) {
                Keys.OrderDirection.ASCENDING -> {
                    when (orderType) {
                        Keys.OrderBy.RENT -> return LowToHighRent
                        Keys.OrderBy.SALE_VALUE -> return LowToHighSale
                        Keys.OrderBy.LISTED_AT -> return OldToNew
                    }
                }
                Keys.OrderDirection.DESCENDING -> {
                    when (orderType) {
                        Keys.OrderBy.RENT -> return HighToLowRent
                        Keys.OrderBy.SALE_VALUE -> return HighToLowSale
                        Keys.OrderBy.LISTED_AT-> return NewToOld
                    }
                }
                else -> throw Exception("case not handled")
            }
            return null
        }
    }
}