package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

class PropertyFinanceResult {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("purchase_price")
    var purchasePrice: Double = 0.0

    @SerializedName("loan_amount")
    var loanAmount: Double = 0.0

    @SerializedName("estimated_value")
    var estimatedValue: Double = 0.0

    @SerializedName("is_rented")
    var isRented: Boolean = false

    @SerializedName("estimated_rent")
    var estimatedRent: Double = 0.0

    @SerializedName("actual_rent")
    var actualRent: Double = 0.0

    @SerializedName("leased_to")
    var leasedTo: String? = null
}
