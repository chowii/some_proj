package com.soho.sohoapp.data.models

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.soho.sohoapp.Dependencies.DEPENDENCIES
import com.soho.sohoapp.data.singletons.SharedUser
import com.soho.sohoapp.utils.Converter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by chowii on 25/7/17.
 */

class User : BasicUser {

    var authenticationToken: String? = null
    var country: String? = null
    var registrationCallback: RegistrationCallback? = null
    var verifications: List<Verification>? = null

    constructor() : super()

    constructor(parcel: Parcel) : super(parcel) {
        authenticationToken = parcel.readString()
        country = parcel.readString()
    }

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
                    SharedUser.getInstance().user = Converter.toUser(user)
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(authenticationToken)
        parcel.writeString(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}