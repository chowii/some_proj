package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName
import com.soho.sohoapp.data.models.InspectionTime

class PropertyListingResult {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("state")
    var state: String? = null

    @SerializedName("receive_sales_offers")
    var canReceiveSalesOffers: Boolean = false

    @SerializedName("receive_rent_offers")
    var canReceiveRentOffers: Boolean = false

    @SerializedName("sale_title")
    var saleTitle: String? = null

    @SerializedName("rent_title")
    var rentTitle: String? = null

    @SerializedName("auction_title")
    var auctionTitle: String? = null

    @SerializedName("discoverable_title")
    var discoverableTitle: String? = null

    @SerializedName("on_site_auction")
    var isOnSiteAuction: Boolean = false

    @SerializedName("auction_date")
    var auctionDate: String? = null

    @SerializedName("auction_time")
    var auctionTime: String? = null

    @SerializedName("rent_payment_frequency")
    var rentPaymentFrequency: String? = null

    @SerializedName("available_from")
    var availableFrom: String? = null

    @SerializedName("appointment_only")
    var isAppointmentOnly: Boolean = false

    @SerializedName("off_site_location")
    var offSiteLocation: LocationResult? = null

    @SerializedName("inspection_times")
    var inspectionTimes: List<InspectionTimeResult>? = null

}
