package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

/**
 * Created by Jovan on 15/9/17.
 */

open class BasicPropertyResult {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("state")
    var state: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("type_of_property")
    var type: String? = null

    @SerializedName("is_investment")
    var isInvestment: Boolean = false

    @SerializedName("is_favourite")
    var isFavourite: Boolean = false

    @SerializedName("rent_price")
    var rentPrice: Int = 0

    @SerializedName("sale_price")
    var salePrice: Int = 0

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("bedrooms")
    var bedrooms: Int = 0

    @SerializedName("bathrooms")
    var bathrooms: Int = 0

    @SerializedName("carspots")
    var carspots: Int = 0

    @SerializedName("location")
    var location: LocationResult? = null

    @SerializedName("photos")
    var photos: List<PhotoResult>? = null

    @SerializedName("agent_logo")
    var agentLogo: ImageResult? = null

    @SerializedName("property_users")
    var propertyUsers: List<PropertyUserResult>? = null
}

