package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import android.content.Context;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseModel;

import java.util.List;

/**
 * Created by chowii on 21/8/17.
 */

public class RadioGroupView implements BaseModel {

    private final List<String> group;

    public RadioGroupView(Context context, List<String> group) {
        this.group = group;
    }

    public List<String> getGroup() {
        return group;
    }

    public int getSize(){ return group.size(); }

    @Override
    public int getItemViewType() {
        return R.layout.item_radio_group;
    }
}
