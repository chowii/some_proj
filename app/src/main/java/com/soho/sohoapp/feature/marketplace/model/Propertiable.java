package com.soho.sohoapp.feature.marketplace.model;

import com.soho.sohoapp.home.BaseModel;

/**
 * Created by chowii on 14/8/17.
 */

public interface Propertiable extends BaseModel {

    int id();
    String state();
    String title();
    int numberOfBedrooms();
    int numberOfBathrooms();
    int numberOfParking();
    String description();
    String typeOfProperty();

    boolean isInvestment();
    double rentPrice();
    double sellPrice();
    boolean isFavourite();

    Object propertyListing(String key);
    Object propertyUser(int position, String key);
    Object location(String key);

}
