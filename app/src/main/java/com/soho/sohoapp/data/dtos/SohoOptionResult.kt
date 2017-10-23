package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

/**
 * Created by Jovan on 9/10/17.
 */

class SohoOptionResult {

    @SerializedName("key")
    var key: String? = null

    @SerializedName("label")
    var label: String? = null

    @SerializedName("meta")
    var category: SohoOptionCategoryResult? = null

    class SohoOptionCategoryResult {

        @SerializedName("category")
        var categoryString: String? = null

    }
}