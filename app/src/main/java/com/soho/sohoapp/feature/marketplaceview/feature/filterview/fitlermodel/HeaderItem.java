package com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel;

import android.support.annotation.LayoutRes;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseFormModel;

/**
 * Created by chowii on 18/8/17.
 */

public class HeaderItem<T> extends BaseFormModel<T> {

    public HeaderItem(T headerText) {
        value = (T) headerText;
    }
    public HeaderItem(T headerText, @LayoutRes int layout) {
        value = (T) headerText;
        this.layout = layout;
    }

    //Default
    private int layout = R.layout.item_filter_header;

    public void setValue(String headerText) { setModelValue((T) headerText); }
    public String getHeaderText() { return (String) getModelValue(); }

    @Override
    protected T getModelValue() { return value == null ? (T) "" : value; }

    @Override
    protected void setModelValue(T value) { this.value = value; }

    @Override
    public int getItemViewType() { return layout; }
    public void setItemViewType(@LayoutRes int layout){ this.layout = layout; }

}
