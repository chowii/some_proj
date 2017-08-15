package com.soho.sohoapp.feature.marketplace;

import com.soho.sohoapp.home.BaseModel;

import java.util.Map;

/**
 * Created by chowii on 14/8/17.
 */

public class SohoProperty implements BaseModel, Propertiable {

    int id;
    String state;
    String title;
    int bedrooms;
    int bathrooms;
    int carspots;
    String description;
    String type_of_property;
    boolean is_investement;
    double rent_price;
    double sell_price;
    boolean is_favourite;

    Map<String, Object> property_listing;
    Map<String, Object> property_user;
    Map<String, Object> location;



    @Override
    public int getItemViewType() {
        return 0;
    }
}
