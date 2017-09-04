package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseFormModel;

/**
 * Created by chowii on 22/8/17.
 */

public class FilterSearchItem extends BaseFormModel<String> {

    public FilterSearchItem() {
        value = "";
    }

    @Override
    protected String getModelValue() {
        return value == null? "" : value;
    }

    @Override
    protected void setModelValue(String value) { this.value = value; }

    public String getValue(){ return getModelValue(); }
    public void setValue(String value){ setModelValue(value); }

    @Override
    public int getItemViewType() { return R.layout.item_filter_search; }
}
