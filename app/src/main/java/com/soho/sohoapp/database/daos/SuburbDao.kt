package com.soho.sohoapp.database.daos

import android.arch.persistence.room.*
import com.soho.sohoapp.database.entities.MarketplaceFilter
import com.soho.sohoapp.database.entities.Suburb
import io.reactivex.Flowable

/**
 * Created by Jovan on 22/9/17.
 */

@Dao
interface  SuburbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateAll(suburbs: List<Suburb>): List<Long>

    @Delete
    fun removeAll(suburbs: List<Suburb>): Int

}