package com.soho.sohoapp.feature.common;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseModel;

public class SohoButton implements BaseModel {
    private String text;

    public SohoButton(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_button;
    }
}