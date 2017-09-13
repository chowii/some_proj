package com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseFormModel;

/**
 * Created by chowii on 23/8/17.
 */

public class ToggleItem extends BaseFormModel<Boolean> {

    private final String buttonText;

    public ToggleItem(String s) {
        buttonText = s;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_filter_toggle;
    }

    public String getText(){ return buttonText; }

    @Override
    protected Boolean getModelValue() {
        return null;
    }

    @Override
    protected void setModelValue(Boolean value) {

    }
}
