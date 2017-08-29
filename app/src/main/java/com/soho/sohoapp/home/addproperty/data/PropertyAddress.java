package com.soho.sohoapp.home.addproperty.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PropertyAddress implements Parcelable {
    private String suburb;
    private String state;
    private String postcode;
    private String country;
    private Double lat;
    private Double lng;
    private String fullAddress;
    private String addressLine1;
    private String addressLine2;

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.suburb);
        dest.writeString(this.state);
        dest.writeString(this.postcode);
        dest.writeString(this.country);
        dest.writeValue(this.lat);
        dest.writeValue(this.lng);
        dest.writeString(this.fullAddress);
        dest.writeString(this.addressLine1);
        dest.writeString(this.addressLine2);
    }

    public PropertyAddress() {
    }

    protected PropertyAddress(Parcel in) {
        this.suburb = in.readString();
        this.state = in.readString();
        this.postcode = in.readString();
        this.country = in.readString();
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.lng = (Double) in.readValue(Double.class.getClassLoader());
        this.fullAddress = in.readString();
        this.addressLine1 = in.readString();
        this.addressLine2 = in.readString();
    }

    public static final Creator<PropertyAddress> CREATOR = new Creator<PropertyAddress>() {
        @Override
        public PropertyAddress createFromParcel(Parcel source) {
            return new PropertyAddress(source);
        }

        @Override
        public PropertyAddress[] newArray(int size) {
            return new PropertyAddress[size];
        }
    };
}
