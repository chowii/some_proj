package com.soho.sohoapp.network.results;

import com.google.gson.annotations.SerializedName;

public class PortfolioPropertyResult {
    @SerializedName("id")
    public int id;

    @SerializedName("state")
    public String state;

    @SerializedName("location")
    public Location location;

    @SerializedName("property_finance")
    public Finance finance;

    public static class Location {
        @SerializedName("address_1")
        public String address1;
    }

    public static class Finance {
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
    }
}
