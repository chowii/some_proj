package com.soho.sohoapp.feature.home.editproperty.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

public class PropertyImage implements Parcelable {
    @DrawableRes
    private int holder;
    @DrawableRes
    private int drawableId;
    private String imageUrl;
    private String filePath;
    private Uri uri;

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(@DrawableRes int drawableId) {
        this.drawableId = drawableId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getHolder() {
        return holder;
    }

    public void setHolder(int holder) {
        this.holder = holder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.holder);
        dest.writeInt(this.drawableId);
        dest.writeString(this.imageUrl);
        dest.writeString(this.filePath);
        dest.writeParcelable(this.uri, flags);
    }

    public PropertyImage() {
        //empty constructor is required because you will not be able to create object
    }

    protected PropertyImage(Parcel in) {
        this.holder = in.readInt();
        this.drawableId = in.readInt();
        this.imageUrl = in.readString();
        this.filePath = in.readString();
        this.uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<PropertyImage> CREATOR = new Creator<PropertyImage>() {
        @Override
        public PropertyImage createFromParcel(Parcel source) {
            return new PropertyImage(source);
        }

        @Override
        public PropertyImage[] newArray(int size) {
            return new PropertyImage[size];
        }
    };
}
