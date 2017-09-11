package com.soho.sohoapp.feature.home.editproperty.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;

import java.util.ArrayList;
import java.util.List;

public class Property implements Parcelable {
    private int id;
    private int bedrooms;
    private int bathrooms;
    private int carspots;
    private String state;
    private String title;
    private String type;
    private PropertyAddress address;
    private PropertyListing propertyListing;
    private List<PropertyImage> propertyImageList;
    private List<PropertyVerification> propertyVerificationList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getCarspots() {
        return carspots;
    }

    public void setCarspots(int carspots) {
        this.carspots = carspots;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PropertyAddress getAddress() {
        return address;
    }

    public void setAddress(PropertyAddress address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    public List<PropertyImage> getPropertyImageList() {
        if (propertyImageList == null) {
            propertyImageList = new ArrayList<>();
        }
        return propertyImageList;
    }

    public void setPropertyImageList(List<PropertyImage> propertyImageList) {
        this.propertyImageList = propertyImageList;
    }

    public PropertyListing getPropertyListing() {
        return propertyListing;
    }

    public void setPropertyListing(PropertyListing propertyListing) {
        this.propertyListing = propertyListing;
    }

    public List<PropertyVerification> getPropertyVerificationList() {
        return propertyVerificationList;
    }

    public void setPropertyVerificationList(List<PropertyVerification> propertyVerificationList) {
        this.propertyVerificationList = propertyVerificationList;
    }

    public Property() {
        //empty constructor is required because you will not be able to create object
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.bedrooms);
        dest.writeInt(this.bathrooms);
        dest.writeInt(this.carspots);
        dest.writeString(this.state);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeParcelable(this.address, flags);
        dest.writeParcelable(this.propertyListing, flags);
        dest.writeTypedList(this.propertyImageList);
        dest.writeTypedList(this.propertyVerificationList);
    }

    protected Property(Parcel in) {
        this.id = in.readInt();
        this.bedrooms = in.readInt();
        this.bathrooms = in.readInt();
        this.carspots = in.readInt();
        this.state = in.readString();
        this.title = in.readString();
        this.type = in.readString();
        this.address = in.readParcelable(PropertyAddress.class.getClassLoader());
        this.propertyListing = in.readParcelable(PropertyListing.class.getClassLoader());
        this.propertyImageList = in.createTypedArrayList(PropertyImage.CREATOR);
        this.propertyVerificationList = in.createTypedArrayList(PropertyVerification.CREATOR);
    }

    public static final Creator<Property> CREATOR = new Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel source) {
            return new Property(source);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };
}
