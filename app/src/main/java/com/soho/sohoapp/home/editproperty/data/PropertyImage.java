package com.soho.sohoapp.home.editproperty.data;

import android.net.Uri;
import android.support.annotation.DrawableRes;

public class PropertyImage {
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
}
