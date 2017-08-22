package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseFormModel;

/**
 * Created by chowii on 20/08/17.
 */

public class CheckboxTitle extends BaseFormModel<Boolean> {

    private String title = "";
    private boolean value = false;


    public CheckboxTitle(String title) { this.title = title; }

    public void setTitle(String title){ this.title = title; }
    public String getTitle(){ return title; }

    @Override
    protected Boolean getModelValue() { return value; }

    @Override
    protected void setModelValue(Boolean value) { this.value = value; }

    public boolean getValue(){ return getModelValue(); }
    public void setValue(boolean value){ setModelValue(value); }

    @Override
    public int getItemViewType() { return R.layout.item_filter_checkbox; }


}
