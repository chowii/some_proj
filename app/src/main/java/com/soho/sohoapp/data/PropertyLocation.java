package com.soho.sohoapp.data;

import com.google.gson.annotations.SerializedName;
import com.soho.sohoapp.network.results.PortfolioPropertyResult;

/**
 * Created by chowii on 6/9/17.
 */

public class PropertyLocation extends PortfolioPropertyResult.Location {

    @SerializedName("address_2")
    private String address2;

    private String suburb;

    private String state;

    private String postcode;

    private String country;

    private double latitude;

    private double longitude;

    @SerializedName("mask_address")
    private boolean isAddressMasked;

    @SerializedName("full_address")
    private String fullAddress;

    public String retrieveAddress1(){ return address1; }

    public String retrieveAddress2(){ return address2; }

    public String retrieveSuburb() { return suburb; }

    public void applySuburbChange(String suburb) { this.suburb = suburb; }

    public String retrieveState() { return state; }

    public String retrievePostcode() { return postcode; }

    public void applyPostcodeChange(String postcode) { this.postcode = postcode; }

    public double retrieveLatitude() { return latitude; }

    public void applyLatitudeChange(double latitude) { this.latitude = latitude; }

    public double retrieveLongitude() { return longitude; }

    public void applyLongitudeChange(double longitude) { this.longitude = longitude; }

    public boolean isAddressMasked() { return isAddressMasked; }

    public void applyAddressMaskedChange(boolean isAddressMasked) { this.isAddressMasked = isAddressMasked; }

    public String retrieveFullAddress() { return fullAddress; }

    public void applyFullAddressChange(String fullAddress) { this.fullAddress = fullAddress; }
}
