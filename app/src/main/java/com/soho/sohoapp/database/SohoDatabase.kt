package com.soho.sohoapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.soho.sohoapp.database.daos.MarketplaceFilterDao
import com.soho.sohoapp.database.daos.SuburbDao
import com.soho.sohoapp.database.entities.MarketplaceFilter
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs
import com.soho.sohoapp.database.entities.Suburb
import io.reactivex.Observable

/**
 * Created by Jovan on 22/9/17.
 */

@Database(entities = arrayOf(MarketplaceFilter::class, Suburb::class), version = 1)
@TypeConverters(Converters::class)
abstract class SohoDatabase : RoomDatabase() {
    abstract fun marketplaceFilterDao(): MarketplaceFilterDao
    abstract fun suburbDao(): SuburbDao
}

//Extensions
fun SohoDatabase.insertReactive(filter: MarketplaceFilterWithSuburbs): Observable<List<Long>> = Observable
        .fromCallable<Long>
        {
            this.marketplaceFilterDao().insertOrUpdateFilter(filter.marketplaceFilter)
        }
        .map<List<Long>> { insertedRowId ->
            for (suburb in filter.suburbs) {
                suburb.marketplaceFilterId = insertedRowId
            }
            return@map this.suburbDao().insertOrUpdateAll(filter.suburbs)
        }

fun SohoDatabase.deleteReactive(filter: MarketplaceFilterWithSuburbs): Observable<Int> = Observable
        .fromCallable<Int>
        {
            this.marketplaceFilterDao().delete(filter.marketplaceFilter)
        }