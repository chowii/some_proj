package com.soho.sohoapp.feature.home.editproperty.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PropertyListing implements Parcelable {
    private int id;
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.state);
    }

    public PropertyListing() {
        //empty constructor is required because you will not be able to create object
    }

    protected PropertyListing(Parcel in) {
        this.id = in.readInt();
        this.state = in.readString();
    }

    public static final Creator<PropertyListing> CREATOR = new Creator<PropertyListing>() {
        @Override
        public PropertyListing createFromParcel(Parcel source) {
            return new PropertyListing(source);
        }

        @Override
        public PropertyListing[] newArray(int size) {
            return new PropertyListing[size];
        }
    };
}
