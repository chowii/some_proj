package com.soho.sohoapp.home.editproperty.data;

import android.support.annotation.DrawableRes;

public class PropertyImage {
    @DrawableRes
    private int drawableId;
    private String imageUrl;
    private String filePath;

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
}
