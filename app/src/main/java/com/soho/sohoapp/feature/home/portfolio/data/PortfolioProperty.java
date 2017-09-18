package com.soho.sohoapp.feature.home.portfolio.data;

import android.support.annotation.LayoutRes;

import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.PropertyFinance;
import com.soho.sohoapp.feature.home.BaseModel;

public class PortfolioProperty implements BaseModel {

    private Location location;
    private PropertyFinance propertyFinance;
    private int viewType;
    private int id;
    private String state;

    @Override
    public int getItemViewType() {
        return viewType;
    }

    public void setItemViewType(@LayoutRes int viewType) {
        this.viewType = viewType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public PropertyFinance getPropertyFinance() {
        return propertyFinance;
    }

    public void setPropertyFinance(PropertyFinance propertyFinance) {
        this.propertyFinance = propertyFinance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
