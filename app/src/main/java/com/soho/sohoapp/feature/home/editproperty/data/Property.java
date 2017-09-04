package com.soho.sohoapp.feature.home.editproperty.data;

import android.support.annotation.NonNull;

import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;

import java.util.ArrayList;
import java.util.List;

public class Property {
    private int id;
    private int bedrooms;
    private int bathrooms;
    private int carspots;
    private String state;
    private String title;
    private String type;
    private PropertyAddress address;
    private List<PropertyImage> propertyImageList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getCarspots() {
        return carspots;
    }

    public void setCarspots(int carspots) {
        this.carspots = carspots;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PropertyAddress getAddress() {
        return address;
    }

    public void setAddress(PropertyAddress address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    public List<PropertyImage> getPropertyImageList() {
        if (propertyImageList == null) {
            propertyImageList = new ArrayList<>();
        }
        return propertyImageList;
    }

    public void setPropertyImageList(List<PropertyImage> propertyImageList) {
        this.propertyImageList = propertyImageList;
    }
}
