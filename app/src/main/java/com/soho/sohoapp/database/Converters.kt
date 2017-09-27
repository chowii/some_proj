package com.soho.sohoapp.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Jovan on 22/9/17.
 */
class Converters {

    @TypeConverter
    fun fromString(value: String): List<String> =
            Gson().fromJson(value, object : TypeToken<ArrayList<String>>() {}.type)

    @TypeConverter
    fun fromArrayList(list: List<String>): String = Gson().toJson(list)
}