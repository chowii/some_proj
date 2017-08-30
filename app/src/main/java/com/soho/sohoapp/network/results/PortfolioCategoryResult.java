package com.soho.sohoapp.network.results;

import com.google.gson.annotations.SerializedName;

public class PortfolioCategoryResult {
    @SerializedName("name")
    public String name;

    @SerializedName("user_id")
    public int userId;

    @SerializedName("property_count")
    public int propertyCount;

    @SerializedName("estimatedValue")
    public double estimatedValue;

    @SerializedName("public_properties_count")
    public int publicPropertiesCount;

    @SerializedName("filter_for_portfolio")
    public String filterForPortfolio;
}
