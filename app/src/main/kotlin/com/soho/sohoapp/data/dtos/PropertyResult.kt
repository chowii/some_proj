package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName


class PropertyResult : BasicPropertyResult() {

    @SerializedName("land_size")
    var landSize: Int = 0

    @SerializedName("land_size_measurement")
    var landSizeMeasurement: String? = null

    @SerializedName("auction_date")
    var auctionDate: String? = null

    @SerializedName("rennovation_details")
    var rennovationDetails: String? = null

    @SerializedName("agent_licence_number")
    var agentLicenseNumber: String? = null

    @SerializedName("agent_mobile_number")
    var agentMobileNumber: String? = null

    @SerializedName("property_listing")
    var propertyListing: PropertyListingResult? = null

    @SerializedName("property_finance")
    var propertyFinance: PropertyFinanceResult? = null

    @SerializedName("verifications")
    var verifications: List<VerificationResult>? = null
}

