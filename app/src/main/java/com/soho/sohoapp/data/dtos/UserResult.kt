package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

/**
 * Created by Jovan on 15/9/17.
 */

class UserResult: BasicUserResult() {

    @SerializedName("authentication_token")
    var authenticationToken: String? = null

    @SerializedName("country")
    var country: String? = null

    @SerializedName("verifications")
    var verifications:List<VerificationResult>? = null

}