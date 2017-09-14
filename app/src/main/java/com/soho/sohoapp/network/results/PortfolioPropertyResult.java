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
    public PropertyFinanceResult finance;

    public static class Location {
        @SerializedName("address_1")
        public String address1;
    }
}
