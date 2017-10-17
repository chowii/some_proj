package com.soho.sohoapp.feature.home.addproperty.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import com.soho.sohoapp.R;

public class PropertyType implements Parcelable {
    public static final String TYPE_CONDO = "condo";
    public static final String TYPE_APARTMENT = "apartment";
    public static final String TYPE_SEMIDUPLEX = "duplex_or_semi";
    public static final String TYPE_HOUSE = "house";
    public static final String TYPE_TERRACE = "terrace";

    private String key;
    private String label;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static int getDefaultImage(String propertyType) {
        switch (propertyType) {
            case TYPE_APARTMENT:
                return R.drawable.apartment;
            case TYPE_CONDO:
                return R.drawable.condo;
            case TYPE_HOUSE:
                return R.drawable.house;
            case TYPE_SEMIDUPLEX:
                return R.drawable.semiduplex;
            case TYPE_TERRACE:
                return R.drawable.terrace;
            default:
                return R.drawable.others;
        }
    }

    @DrawableRes
    public static int getIcon(String propertyType) {
        switch (propertyType) {
            case TYPE_APARTMENT:
                return R.drawable.ic_apartment;
            case TYPE_CONDO:
                return R.drawable.ic_condo;
            case TYPE_HOUSE:
                return R.drawable.ic_house;
            case TYPE_SEMIDUPLEX:
                return R.drawable.ic_semiduplex;
            default:
                return 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.label);
    }

    public PropertyType() {
    }

    protected PropertyType(Parcel in) {
        this.key = in.readString();
        this.label = in.readString();
    }

    public static final Creator<PropertyType> CREATOR = new Creator<PropertyType>() {
        @Override
        public PropertyType createFromParcel(Parcel source) {
            return new PropertyType(source);
        }

        @Override
        public PropertyType[] newArray(int size) {
            return new PropertyType[size];
        }
    };
}
