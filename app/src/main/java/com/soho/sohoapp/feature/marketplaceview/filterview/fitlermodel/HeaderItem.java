package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseFormModel;

/**
 * Created by chowii on 18/8/17.
 */

public class HeaderItem<T> extends BaseFormModel<T> {

    public HeaderItem(String headerText) {
        value = (T) headerText;
    }

    public void setValue(String headerText) { setModelValue((T) headerText); }
    public String getHeaderText() { return (String) getModelValue(); }

    @Override
    protected T getModelValue() { return value == null ? (T) "" : value; }

    @Override
    protected void setModelValue(T value) { this.value = value; }

    @Override
    public int getItemViewType() { return R.layout.item_filter_header; }

}
