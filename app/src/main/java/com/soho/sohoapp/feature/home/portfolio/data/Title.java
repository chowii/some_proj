package com.soho.sohoapp.feature.home.portfolio.data;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseModel;

public class Title implements BaseModel {
    private String title;

    public Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_title;
    }
}
