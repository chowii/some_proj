package com.soho.sohoapp.feature.home.editproperty.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PropertyVerification implements Parcelable {
    private int id;
    private String type;
    private String text;
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        dest.writeString(this.type);
        dest.writeString(this.text);
        dest.writeString(this.state);
    }

    public PropertyVerification() {
        //empty constructor is required because you will not be able to create object
    }

    protected PropertyVerification(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.text = in.readString();
        this.state = in.readString();
    }

    public static final Creator<PropertyVerification> CREATOR = new Creator<PropertyVerification>() {
        @Override
        public PropertyVerification createFromParcel(Parcel source) {
            return new PropertyVerification(source);
        }

        @Override
        public PropertyVerification[] newArray(int size) {
            return new PropertyVerification[size];
        }
    };
}
