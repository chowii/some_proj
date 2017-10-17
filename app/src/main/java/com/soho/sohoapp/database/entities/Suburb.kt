package com.soho.sohoapp.database.entities

import android.arch.persistence.room.*

/**
 * Created by Jovan on 21/9/17.
 */

@Entity(tableName = "suburbs",
        foreignKeys = arrayOf(ForeignKey(entity = MarketplaceFilter::class,
                                  parentColumns =  arrayOf("id"),
                                  childColumns = arrayOf("marketplace_filter_id"),
                                  onDelete = ForeignKey.CASCADE)))
data class Suburb(
        @PrimaryKey(autoGenerate = true)
        var id:Long = 0,
        var placeId: String = "",
        var name: String = "",
        @ColumnInfo(name = "marketplace_filter_id")
        var marketplaceFilterId: Long = 0) {

    @Ignore
    var secondaryText:String? = null

    @Ignore
    constructor() : this(0, "", "", 0)

    @Ignore
    constructor(placeId: String, name:String): this(0, placeId, name, 0)

    @Ignore
    constructor(suburbToClone:Suburb): this(0, suburbToClone.placeId, suburbToClone.name, 0)

}
