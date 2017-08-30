package com.soho.sohoapp.feature.marketplaceview.filterview.savedfilters.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseModel;

import java.util.List;

/**
 * Created by chowii on 30/8/17.
 */

public class SavedFilterItem implements BaseModel {

    @Override
    public int getItemViewType() { return R.layout.item_filter_saved; }

    @SerializedName("by_google_places[place_ids]")
    private List<String> title;

    @SerializedName("by_min_rent_price")
    private String minRent ;

    @SerializedName("by_min_sale_price")
    private String minSale;

    @SerializedName("by_max_rent_price")
    private String maxRent;

    @SerializedName("by_max_sale_price")
    private String maxSale ;

    private String subTitle = "";

    @Nullable
    public String getTitle(){
        StringBuilder builder = new StringBuilder();
        for (String s : title) builder.append(s + ",");

        return builder.toString().isEmpty() ? "Suburb - Suburb" : builder.toString();
    }

    @Nullable
    public String getSubTitle() {
        String min;
        min = minSale == null ? minRent == null ? "Min" : minRent : minSale ;
        String max = maxSale == null ? maxRent == null ? "Max" : maxRent : maxSale ;

        setSubTitle(min + " - " + max);
        return subTitle == null ? "" : subTitle;
    }

    private void setSubTitle(String subTitle) {
        if(subTitle == null ) this.subTitle = "";
        else this.subTitle = subTitle;
    }
}
