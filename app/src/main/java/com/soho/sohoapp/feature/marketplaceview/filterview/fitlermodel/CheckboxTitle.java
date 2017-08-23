package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseFormModel;

/**
 * Created by chowii on 20/08/17.
 */

public class CheckboxTitle extends BaseFormModel<Boolean> {

    private boolean itemValue = false;

    private String key = "";
    private String label = "";

    public CheckboxTitle(String title) { this.label = title; }

    public void setTitle(String title){ this.key = title; }
    public String getTitle(){ return label == null ? "" : label; }

    @Override
    protected Boolean getModelValue() { return itemValue; }

    @Override
    protected void setModelValue(Boolean value) { this.itemValue = value; }

    public boolean getValue(){ return getModelValue(); }
    public void setValue(boolean value){ setModelValue(value); }

    @Override
    public int getItemViewType() { return R.layout.item_filter_checkbox; }


}
