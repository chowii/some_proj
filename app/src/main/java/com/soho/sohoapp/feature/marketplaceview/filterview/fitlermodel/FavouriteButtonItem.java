package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import com.soho.sohoapp.R;

/**
 * Created by chowii on 23/8/17.
 */

public class FavouriteButtonItem extends ButtonItem {

    public FavouriteButtonItem(String s) {
        super(s);
    }

    @Override
    public int getItemViewType() { return R.layout.item_favourite_button; }
}
