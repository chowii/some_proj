package com.soho.sohoapp.feature.marketplaceview.filterview.savedfilters.model;

import android.support.annotation.Nullable;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseModel;

/**
 * Created by chowii on 30/8/17.
 */

public class SavedFilterItem implements BaseModel {

    @Override
    public int getItemViewType() { return R.layout.item_filter_saved; }

    private String title;
    private String subTitle;

//    public SavedFilterItem() {
////        this.title = title;
////        this.subTitle = subTitle;
//    }

    @Nullable
    public String getTitle(){ return title == null ? "" : title; }

    public void setTitle(String title){
        if(title == null ) this.title = "";
        else this.title = title;
    }

    @Nullable
    public String getSubTitle() { return subTitle == null ? "" : subTitle; }

    public void setSubTitle(String subTitle) {
        if(subTitle == null ) this.subTitle = "";
        else this.subTitle = title;
    }
}
