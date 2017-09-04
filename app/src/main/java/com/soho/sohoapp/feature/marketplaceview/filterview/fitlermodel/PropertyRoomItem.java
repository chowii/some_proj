package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseFormModel;

/**
 * Created by chowii on 31/8/17.
 */

public class PropertyRoomItem extends BaseFormModel {

    @Override
    public int getItemViewType() {
        return R.layout.item_rooms;
    }

    @Override
    protected Object getModelValue() {
        return null;
    }

    @Override
    protected void setModelValue(Object value) {

    }
}
