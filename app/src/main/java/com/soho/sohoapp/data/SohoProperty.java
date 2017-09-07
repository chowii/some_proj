package com.soho.sohoapp.data;

import android.support.annotation.NonNull;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.model.Propertyable;
import com.soho.sohoapp.helper.DateHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by chowii on 14/8/17.
 */

public class SohoProperty implements Propertyable {

    private int id;
    private String state;
    private String title;
    private int bedrooms;
    private int bathrooms;
    private int carspots;
    private String description;
    private String type_of_property;
    private boolean is_investment;
    private double rent_price;
    private double sell_price;
    private String updated_at;

    private boolean is_favourite;

    private PropertyListing property_listing;
    private List<Map<String, Object>> property_user;
    private PropertyLocation location;


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
    public String typeOfProperty() {
        if(type_of_property == null) return "";
        type_of_property = toSentenceCase(type_of_property.replace("_", " "));
        return type_of_property;
    }

    @Override
    public boolean isInvestment() { return is_investment; }

    @Override
    public double rentPrice() { return rent_price; }

    @Override
    public double sellPrice() { return sell_price; }

    @Override
    public Date lastUpdatedAt() {
        if(updated_at == null) return new Date(System.currentTimeMillis());
        return DateHelper.stringToDate(updated_at, "yyyy-MM-dd'T'HH:mm:ss.SSS");
    }

    @Override
    public boolean isFavourite() { return is_favourite; }

    @Override
    public PropertyListing propertyListing() {
        if(property_listing == null) return new PropertyListing();
        return property_listing;
    }

    @Override
    public Object propertyUser(int position, String key) {
        if(key.isEmpty()) return null;
        return property_user.get(position).get(key);
    }

    @Override
    public PropertyLocation location() { return location; }

    @NonNull
    private String toSentenceCase(String key) {
        int keyFirstChar = key.charAt(0) - 32;
        return String.valueOf((char) keyFirstChar) + key.substring(1);
    }

}
