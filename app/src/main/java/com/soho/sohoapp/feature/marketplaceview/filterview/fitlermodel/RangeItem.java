package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseFormModel;

/**
 * Created by chowii on 22/8/17.
 */

public class RangeItem<T> extends BaseFormModel<T> {

    @Override
    protected T getModelValue() {
        return value;
    }

    @Override
    protected void setModelValue(T value) {
        this.value = value;
    }

    public T getValue() { return getModelValue(); }
    public void setValue(T value) { setModelValue(value); }

    @Override
    public int getItemViewType() {
        return R.layout.item_filter_range;
    }
}
