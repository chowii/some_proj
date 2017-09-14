package com.soho.sohoapp.feature.home.portfolio.data;

import android.support.annotation.LayoutRes;

import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;

public class PortfolioProperty implements BaseModel {

    private PropertyAddress propertyAddress;
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

    public PropertyAddress getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(PropertyAddress propertyAddress) {
        this.propertyAddress = propertyAddress;
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
