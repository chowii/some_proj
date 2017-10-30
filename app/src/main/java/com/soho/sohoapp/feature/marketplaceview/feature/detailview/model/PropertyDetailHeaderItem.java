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
    private double bedroom;
    private double bathroom;
    private double carspot;
    private int propertySize;
    private String landSizeMeasurament;
    private String propertyType;
    private String propertyState;
    private PropertyUser representingUser;

    private static String PROPERTY_DEFAULT_TEXT = getStringFromResource(R.string.property_detail_header_no_header_text);

    @Override
    public int getItemViewType() {
        return R.layout.item_property_detail_header;
    }

    public String getHeader() {
        return header == null ? PROPERTY_DEFAULT_TEXT : header;
    }

    public void setHeader(String header) {
        if (header == null) this.header = PROPERTY_DEFAULT_TEXT;
        else this.header = header;
    }

    public void setLandSizeMeasurament(String landSizeMeasurament) {
        this.landSizeMeasurament = landSizeMeasurament;
    }

    public double getBathroom() {
        return bathroom;
    }

    public void setBathroom(double bathroom) {
        this.bathroom = (bathroom);
    }

    public double getBedroom() {
        return bedroom;
    }

    public void setBedroom(double bedroom) {
        this.bedroom = bedroom;
    }

    public double getCarspot() {
        return carspot;
    }

    public void setCarspot(double carspot) {
        this.carspot = carspot;
    }

    public int getPropertySize() {
        return propertySize;
    }

    public void setPropertySize(int propertySize) {
        if (propertySize < 0) this.propertySize = 0;
        else this.propertySize = propertySize;
    }

    public String getPropertySizeWithUnit() {
        return String.format(
                Locale.getDefault(),
                getStringFromResource(R.string.integer_space_string_format_string),
                propertySize,
                landSizeMeasurament
        );
    }

    public void applyPropertyTypeChange(String propertyType) {
        this.propertyType = propertyType;
    }

    public String retrievePropertyType() {
        return propertyType == null ? getStringFromResource(R.string.unknown) : propertyType;
    }

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
