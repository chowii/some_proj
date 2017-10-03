package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

/**
 * Created by Jovan on 15/9/17.
 */

open class BasicUserResult {

    @SerializedName("email")
    var email: String? = null

    @SerializedName("first_name")
    var firstName: String? = null

    @SerializedName("last_name")
    var lastName: String? = null

    @SerializedName("dob")
    var dateOfBirth: String? = null

    @SerializedName("avatar")
    var avatar: ImageResult? = null

    @SerializedName("profile_complete")
    var isProfileComplete: Boolean? = null

}