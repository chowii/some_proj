package com.soho.sohoapp.feature.marketplaceview.feature.detailview.model;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.models.PropertyUser;
import com.soho.sohoapp.feature.home.BaseModel;

import java.util.Locale;

import static com.soho.sohoapp.SohoApplication.getStringFromResource;

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
    private String propertyState;
    private PropertyUser representingUser;

    private static String PROPERTY_DEFAULT_TEXT = getStringFromResource(R.string.property_detail_header_no_header_text);

    @Override
    public int getItemViewType() { return R.layout.item_property_detail_header; }

    public String getHeader() { return header == null ? PROPERTY_DEFAULT_TEXT : header; }

    public void setHeader(String header) {
        if (header == null) this.header = PROPERTY_DEFAULT_TEXT;
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
        if (propertySize < 0) this.propertySize = 0;
        else this.propertySize = propertySize;
    }

    public String getSizeUnit() { return sizeUnit == null ? " \u33A1" : sizeUnit; }

    public void setSizeUnit(String sizeUnit) {
        if (sizeUnit == null) sizeUnit = "";
        else this.sizeUnit = sizeUnit;
    }

    public String getPropertySizeWithUnit(){
        return String.format(
                Locale.getDefault(),
                getStringFromResource(R.string.integer_space_string_format_string),
                propertySize,
                getSizeUnit()
        );
    }

    public void applyPropertyTypeChange(String propertyType) {
        this.propertyType = propertyType;
    }

    public String retrievePropertyType() { return propertyType == null ? "Unknown" : propertyType; }

    public String getPropertyState() {
        return propertyState;
    }

    public void setPropertyState(String propertyState) {
        this.propertyState = propertyState;
    }

    public PropertyUser getRepresentingUser() {
        return representingUser;
    }

    public void setRepresentingUser(PropertyUser representingUser) {
        this.representingUser = representingUser;
    }
}
