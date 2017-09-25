package com.soho.sohoapp.preferences

import android.content.Context
import com.google.gson.Gson
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.data.models.User

class Prefs(context: Context) {
    private val SHARED_PREFS_AUTH_TOKEN: String = "SHARED_PREFS_AUTH_TOKEN"
    private val prefs= context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    var authToken: String
        set(value) {
            prefs.edit()?.putString(SHARED_PREFS_AUTH_TOKEN, value)?.apply()
            DEPENDENCIES.logger.d(prefs.getString(SHARED_PREFS_AUTH_TOKEN, ""))
        }
        get() {
            return prefs.getString(SHARED_PREFS_AUTH_TOKEN, "")
        }
    var mUser: User? = null

    var hasInstalled: Boolean
        set(value) {
            prefs.edit()?.putBoolean("FirstInstall", value)?.apply()
        }
        get() = prefs.getBoolean("FirstInstall", false)


    fun putGsonObject(key: String, value: Any) {

        val gson = Gson()
        val json: String = gson.toJson(value)

        putString(key, json)
    }

    fun <T> getGsonObject(key: String, clazz: Class<T>): T {
        val json = prefs.getString(key, "")
        val gson = Gson()
        val gsonObj: T = gson.fromJson(json, clazz)

        return gsonObj
    }

    private fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    companion object {
        private val SHARED_PREFS_NAME = "Soho-prefs"
    }

}
