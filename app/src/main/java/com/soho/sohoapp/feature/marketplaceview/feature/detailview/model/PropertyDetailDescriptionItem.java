package com.soho.sohoapp.feature.marketplaceview.feature.detailview.model;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseModel;

/**
 * Created by chowii on 1/9/17.
 */

public class PropertyDetailDescriptionItem implements BaseModel {

    private String description;

    public PropertyDetailDescriptionItem() {}

    public PropertyDetailDescriptionItem(String description) {
        setDescription(description);
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_description;
    }

    public String getDescription(){ return description == null ? "No description is available." : description; }

    public void setDescription(String description){
        if(description == null) this.description = "No description is available.";
        else this.description = description;
    }

}
