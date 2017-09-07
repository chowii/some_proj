package com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseFormModel;

/**
 * Created by chowii on 23/8/17.
 */

public class ButtonItem extends BaseFormModel<Boolean> {

    private String buttonText = "";
    private boolean isChecked;

    public ButtonItem(String buttonText) {
        this.buttonText = buttonText;
    }

    @Override
    protected Boolean getModelValue() {
        return isChecked;
    }

    @Override
    protected void setModelValue(Boolean value) { isChecked = value; }

    public void setValue(boolean value){ setModelValue(value); }
    public boolean getValue(){ return getModelValue(); }

    public String getButtonText(){ return buttonText; }

    @Override
    public int getItemViewType() { return R.layout.item_button; }
}
