package com.soho.sohoapp.feature

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by chowii on 25/7/17.
 */
class User {

    var email: String? = null

    @SerializedName("first_name")
    var firstName: String? = null

    @SerializedName("last_name")
    var lastName: String? = null

    @SerializedName("dob")
    lateinit var dateOfBirth: String

    var country: String? = null

    @SerializedName("authentication_token")
    var authenticationToken: String? = null

    var registrationCallback: RegistrationCallback? = null

    fun getFullnameShort() = String.format(Locale.getDefault(), "%s.%s", firstName?.get(0), lastName)

    fun getFullname() = String.format(Locale.getDefault(), "%s %s", firstName, lastName)


    fun registerUser(registerMap: Map<String, String>): Disposable? {
        return DEPENDENCIES.sohoService.register(registerMap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    Log.v("LOG_TAG---", user.authenticationToken)
                    email = user.email
                    firstName = user.firstName
                    DEPENDENCIES.preferences.authToken = user.authenticationToken ?: ""
                    SharedUser.getInstance().user = user
                    this.registrationCallback?.onRegistrationSuccessful()
                }, {
                    error ->
                    Log.e("LOG_TAG---", error.message)
                })
    }

    fun updateUserProfile(updateMap: Map<String, String>): Disposable? {
        return DEPENDENCIES.sohoService.updateUserProfile(updateMap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    firstName = user.firstName
                    lastName = user.lastName
                    country = user.country
                    this.registrationCallback?.onRegistrationSuccessful()
                }, {
                    error ->
                    Log.e("LOG_TAG---", error.message)
                })
    }

    interface RegistrationCallback {
        fun onRegistrationSuccessful()
    }
}

class SharedUser {
    var user: User? = null
        set(value) {
            field = value
        }


    companion object {

        private var uniqueInstance: SharedUser? = null
        fun getInstance(): SharedUser {
            if (uniqueInstance == null) {
                uniqueInstance = SharedUser()
                return uniqueInstance as SharedUser
            }
            return uniqueInstance as SharedUser
        }
    }
}