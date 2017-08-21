package com.soho.sohoapp.data;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplace.model.Propertyable;

import java.util.List;
import java.util.Map;

/**
 * Created by chowii on 14/8/17.
 */

public class SohoProperty implements Propertyable {

    int id;
    String state;
    String title;
    int bedrooms;
    int bathrooms;
    int carspots;
    String description;
    String type_of_property;
    boolean is_investment;
    double rent_price;
    double sell_price;
    boolean is_favourite;

    Map<String, Object> property_listing;
    List<Map<String, Object>> property_user;
    Map<String, Object> location;


    @Override
    public int getItemViewType() { return R.layout.item_property; }

    @Override
    public int id() {return id; }

    @Override
    public String state() { return state == null ? "" : state; }

    @Override
    public String title() { return title == null ? "" : title; }

    @Override
    public int numberOfBedrooms() { return bedrooms; }

    @Override
    public int numberOfBathrooms() { return bathrooms; }

    @Override
    public int numberOfParking() {  return carspots; }

    @Override
    public String description() { return description == null ? "" : description; }

    @Override
    public String typeOfProperty() { return type_of_property == null ?  "" : type_of_property; }

    @Override
    public boolean isInvestment() { return is_investment; }

    @Override
    public double rentPrice() { return rent_price; }

    @Override
    public double sellPrice() { return sell_price; }

    @Override
    public boolean isFavourite() { return is_favourite; }

    @Override
    public Object propertyListing(String key) {
        if(key.isEmpty()) return null;
        return property_listing.get(key);
    }

    @Override
    public Object propertyUser(int position, String key) {
        if(key.isEmpty()) return null;
        return property_user.get(position).get(key);
    }

    @Override
    public Object location(String key) {
        if(key.isEmpty()) return null;
        return location.get(key);
    }

}
