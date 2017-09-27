package com.soho.sohoapp.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.soho.sohoapp.database.entities.MarketplaceFilter
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs
import io.reactivex.Maybe

/**
 * Created by Jovan on 22/9/17.
 */

@Dao
interface  MarketplaceFilterDao {

    @Query("SELECT * FROM marketplace_filters")
    fun getAllMarketplaceFilters(): Maybe<List<MarketplaceFilterWithSuburbs>>

    @Query("SELECT * FROM marketplace_filters WHERE is_current_filter == 1 LIMIT 1")
    fun getCurrentMarketplaceFilter(): Maybe<MarketplaceFilterWithSuburbs>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateFilter(filter:MarketplaceFilter): Long

}