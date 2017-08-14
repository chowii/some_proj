package com.soho.sohoapp.dev.feature

import com.google.gson.annotations.SerializedName

/**
 * Created by chowii on 25/7/17.
 */
class User {

    var email: String? = null

    @SerializedName("authentication_token")
    var authenticationToken: String? = null

}