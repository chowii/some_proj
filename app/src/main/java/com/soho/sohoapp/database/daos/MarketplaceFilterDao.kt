package com.soho.sohoapp.database.daos

import android.arch.persistence.room.*
import com.soho.sohoapp.database.entities.MarketplaceFilter
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs
import io.reactivex.Maybe

/**
 * Created by Jovan on 22/9/17.
 */

@Dao
interface  MarketplaceFilterDao {

    @Query("SELECT * FROM marketplace_filters WHERE is_current_filter == 0")
    fun getAllNonCurrentFilters(): Maybe<List<MarketplaceFilterWithSuburbs>>

    @Query("SELECT * FROM marketplace_filters")
    fun getAllFilters(): Maybe<List<MarketplaceFilterWithSuburbs>>

    @Query("SELECT * FROM marketplace_filters WHERE is_current_filter == 1 LIMIT 1")
    fun getCurrentMarketplaceFilter(): Maybe<MarketplaceFilterWithSuburbs>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateFilter(filter:MarketplaceFilter): Long

    @Delete
    fun delete(filter:MarketplaceFilter): Int

}