package com.soho.sohoapp.feature.marketplaceview.feature.detailview.model;

import com.soho.sohoapp.R;
import com.soho.sohoapp.SohoApplication;
import com.soho.sohoapp.feature.home.BaseModel;

/**
 * Created by chowii on 1/9/17.
 */

public class PropertyDetailDescriptionItem implements BaseModel {

    private String description;
    private String noDescriptionText ;

    public PropertyDetailDescriptionItem() {
        if(noDescriptionText == null)
            description = SohoApplication.getContext().getString(R.string.no_description_available_description_item);
    }

    public PropertyDetailDescriptionItem(String description) {
        setDescription(description);
        if(noDescriptionText == null)
            description = SohoApplication.getContext().getString(R.string.no_description_available_description_item);
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_description;
    }

    public String getDescription(){ return description == null ? noDescriptionText : description; }

    public void setDescription(String description){
        if (description == null) this.description = noDescriptionText;
        else this.description = description;
    }

}
