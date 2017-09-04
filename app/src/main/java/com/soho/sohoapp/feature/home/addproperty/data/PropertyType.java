package com.soho.sohoapp.feature.home.addproperty.data;

import com.soho.sohoapp.R;

public class PropertyType {
    public static final String TYPE_CONDO = "condo";
    public static final String TYPE_APARTMENT = "apartment";
    public static final String TYPE_SEMIDUPLEX = "duplex_or_semi";
    public static final String TYPE_HOUSE = "house";
    public static final String TYPE_TERRACE = "terrace";

    private String key;
    private String label;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static int getDefaultImage(String propertyType) {
        switch (propertyType) {
            case TYPE_APARTMENT:
                return R.drawable.apartment;
            case TYPE_CONDO:
                return R.drawable.condo;
            case TYPE_HOUSE:
                return R.drawable.house;
            case TYPE_SEMIDUPLEX:
                return R.drawable.semiduplex;
            case TYPE_TERRACE:
                return R.drawable.terrace;
            default:
                return R.drawable.others;
        }
    }
}
