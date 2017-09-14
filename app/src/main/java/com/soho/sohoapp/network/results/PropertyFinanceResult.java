package com.soho.sohoapp.network.results;

import com.google.gson.annotations.SerializedName;

public class PropertyFinanceResult {
    @SerializedName("id")
    public int id;

    @SerializedName("purchase_price")
    public int purchasePrice;

    @SerializedName("loan_amount")
    public int loanAmount;

    @SerializedName("estimated_value")
    public int estimatedValue;

    @SerializedName("is_rented")
    public boolean isRented;

    @SerializedName("estimated_rent")
    public int estimatedRent;

    @SerializedName("actual_rent")
    public int actualRent;

    @SerializedName("leased_to")
    public String leasedTo;
}
