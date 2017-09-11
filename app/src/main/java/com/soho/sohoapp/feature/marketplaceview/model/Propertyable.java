package com.soho.sohoapp.feature.marketplaceview.model;

import com.soho.sohoapp.data.PropertyListing;
import com.soho.sohoapp.data.PropertyLocation;
import com.soho.sohoapp.feature.home.BaseModel;

import java.util.Calendar;

/**
 * Created by chowii on 14/8/17.
 */

public interface Propertyable extends BaseModel {

    int id();
    String state();
    String title();
    int numberOfBedrooms();
    int numberOfBathrooms();
    int numberOfParking();
    String description();
    String typeOfProperty();
    String retrieveDisplayableTypeOfProperty();

    boolean isInvestment();
    double rentPrice();
    double sellPrice();
    Calendar lastUpdatedAt();
    String retrieveDisplayableLastUpdatedAt();

    boolean isFavourite();
    PropertyListing propertyListing();
    Object propertyUser(int position, String key);

    PropertyLocation location();
}
