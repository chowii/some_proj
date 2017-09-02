package com.soho.sohoapp.feature.marketplaceview.model;

import android.support.annotation.NonNull;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseModel;

/**
 * Created by chowii on 30/8/17.
 */

public class EmptyDataSetItem implements BaseModel {

    @Override
    public int getItemViewType() {
        return R.layout.item_empty_dataset;
    }

    private String header;
    private String subHeader;

    @NonNull
    public String header() { return this.header == null ? "" : header; }

    @NonNull
    public String subHeader() { return this.subHeader == null ? "" : subHeader; }
}
