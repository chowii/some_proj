package com.soho.sohoapp.network.results;

import com.google.gson.annotations.SerializedName;
import com.soho.sohoapp.data.dtos.LocationResult;
import com.soho.sohoapp.data.dtos.PropertyFinanceResult;

public class PortfolioPropertyResult {
    @SerializedName("id")
    public int id;

    @SerializedName("state")
    public String state;

    @SerializedName("location")
    public LocationResult location;

    @SerializedName("property_finance")
    public PropertyFinanceResult finance;
}
