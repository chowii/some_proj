package com.soho.sohoapp.home.editproperty.data;

import android.support.annotation.DrawableRes;

public class PropertyImage {
    @DrawableRes
    private int drawableId;
    private String imageUrl;

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(@DrawableRes int drawableId) {
        this.drawableId = drawableId;
    }
}
