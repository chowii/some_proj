package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseModel;

/**
 * Created by chowii on 20/08/17.
 */

public class CheckboxTitle implements BaseModel {

    private String title = "";

    public CheckboxTitle(String title) { this.title = title; }

    public void setTitle(String title){ this.title = title; }
    public String getTitle(){ return title; }

    @Override
    public int getItemViewType() { return R.layout.item_filter_checkbox; }


}
