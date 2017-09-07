package com.soho.sohoapp.feature.marketplaceview.feature.detailview.model;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseModel;

/**
 * Created by chowii on 1/9/17.
 */

public class PropertyDetailHeaderItem implements BaseModel {

    private String header;
    private int bedroom;
    private int bathroom;
    private int carspot;
    private int propertySize;
    private String sizeUnit;
    private String propertyType;


    @Override
    public int getItemViewType() { return R.layout.item_property_detail_header; }

    public String getHeader() { return header == null ? "No title available." : header; }

    public void setHeader(String header) {
        if(header == null) this.header = "No title available.";
        else this.header = header;
    }

    public int getBathroom() { return bathroom; }

    public void setBathroom(int bathroom) { this.bathroom = (bathroom); }

    public int getBedroom() { return bedroom; }

    public void setBedroom(int bedroom) { this.bedroom = bedroom; }

    public int getCarspot() { return carspot; }

    public void setCarspot(int carspot) { this.carspot = carspot; }

    public int getPropertySize() { return propertySize; }

    public void setPropertySize(int propertySize) {
        if(propertySize < 0) this.propertySize = 0;
        else this.propertySize = propertySize;
    }

    public String getSizeUnit() { return sizeUnit == null ? " \u33A1" : sizeUnit; }

    public void setSizeUnit(String sizeUnit) {
        if(sizeUnit == null) sizeUnit =
        this.sizeUnit = sizeUnit;
    }

    public String getPropertySizeWithUnit(){
        String propertySizeWithUnit =  propertySize + (sizeUnit == null ? " \u33A1" : sizeUnit) ;
        return propertySizeWithUnit;
    }

    public void applyPropertyTypeChange(String propertyType) {
        this.propertyType = propertyType;
    }

    public String retrievePropertyType() { return propertyType == null ? "Unknown" : propertyType; }
}
