package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseModel;

/**
 * Created by chowii on 18/8/17.
 */

public class HeaderItem implements BaseModel {

    private String headerText = "";

    public HeaderItem(String headerText) { this.headerText = headerText; }

    public void setHeaderText(String headerText) { this.headerText = headerText; }
    public String getHeaderText() { return headerText; }

    @Override
    public int getItemViewType() { return R.layout.item_filter_header; }

}
