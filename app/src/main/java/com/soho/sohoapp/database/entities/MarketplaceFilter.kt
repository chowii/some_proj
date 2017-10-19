package com.soho.sohoapp.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.soho.sohoapp.data.enums.MarketplaceFilterSaleType
import com.soho.sohoapp.data.enums.RentPaymentFrequency
import com.soho.sohoapp.extensions.toShortHand

/**
 * Created by Jovan on 21/9/17.
 */
@Entity(tableName = "marketplace_filters")
data class MarketplaceFilter(
        @PrimaryKey(autoGenerate = true)
        var id:Long,
        @ColumnInfo(name = "is_current_filter")
        var isCurrentFilter:Boolean = false,
        var saleType:String = MarketplaceFilterSaleType.SALE,
        var rentPaymentFrequency: String = RentPaymentFrequency.WEEKLY,
        var radius:Int = 25,
        var propertyTypes:List<String> = ArrayList(),
        var priceFromRent:Int = 0,
        var priceToRent:Int = 0,
        var priceFromBuy:Int = 0,
        var priceToBuy:Int = 0,
        var bedrooms:Double = 0.0,
        var bathrooms:Double = 0.0,
        var carspots:Double = 0.0,
        var allProperties:Boolean = true ) {

    companion object {
        private val DEFAULT_RADIUS = 25
        private val DEFAULT_BEDROOMS = 1.0
    }

    @Ignore
    constructor() :this(0, false, MarketplaceFilterSaleType.SALE, RentPaymentFrequency.WEEKLY, DEFAULT_RADIUS, ArrayList<String>(), 0, 0, 0, 0, DEFAULT_BEDROOMS, 0.0, 0.0, true)

    val isSaleFilter: Boolean
        get() = saleType == MarketplaceFilterSaleType.SALE

    val isRentFilter: Boolean
        get() = saleType == MarketplaceFilterSaleType.RENT

    val toPrice:Int
        get() = if(isSaleFilter) priceToBuy else priceToRent

    val fromPrice:Int
        get() = if(isSaleFilter) priceFromBuy else priceFromRent

    fun setToPrice(price:Int) {
        if(isSaleFilter) priceToBuy = price else priceToRent = price
    }

    fun setFromPrice(price:Int) {
        if(isSaleFilter) priceFromBuy = price else priceFromRent = price
    }

    fun addPropertyType(type:String) {
        if(!propertyTypes.contains(type) && !type.isEmpty())
            propertyTypes += type
    }

    fun removePropertyType(type:String) {
        if(propertyTypes.contains(type))
            propertyTypes -= type
    }

    /**
     * @param rangeFormat the format of the overall string e.g. '%1$s - %2$s'
     * @param dollarFormat the format of the price value e.g. '$%1$s'
     * @param anyString the text for when the price value is 0 e.g. 'Any'
     * @return the returned string following the above format constraints e.g. '$100k - Any'
     */
    fun priceRangeDisplayString(rangeFormat:String, dollarFormat:String, anyString:String): String {
        val fromPriceString = if(fromPrice == 0) anyString else dollarFormat.format(fromPrice.toShortHand())
        val toPriceString = if(toPrice == 0) anyString else dollarFormat.format(toPrice.toShortHand())
        return rangeFormat.format(fromPriceString, toPriceString)
    }
}