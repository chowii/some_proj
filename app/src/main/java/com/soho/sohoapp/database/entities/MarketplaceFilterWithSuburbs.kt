package com.soho.sohoapp.database.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

/**
 * Created by Jovan on 22/9/17.
 */
class MarketplaceFilterWithSuburbs {

    @Embedded
    var filter:MarketplaceFilter = MarketplaceFilter()

    @Relation(parentColumn = "id", entityColumn = "marketplace_filter_id", entity = Suburb::class)
    var suburbs:List<Suburb> = ArrayList()

    val suburbNames:List<String>
        get() = suburbs.map { it.name }

    /**
     * @param multipleSuburbsFormat format to follow if there are multiple suburbs
     * @param noSuburbsString default message if no suburbs selected
     * @return string following format e.g. 'Artarmon & 1 more' or 'Artarmon' or the default
     */
    fun suburbsDisplayString(multipleSuburbsFormat:String, noSuburbsString:String): String {
        if(suburbs.size > 1) return multipleSuburbsFormat.format(suburbs[0].name, suburbs.size - 1)
        if(suburbs.size == 1) return suburbs[0].name
        return noSuburbsString
    }
}