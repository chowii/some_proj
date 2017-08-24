package com.soho.sohoapp.home.portfolio.data;

import android.support.annotation.LayoutRes;

import com.soho.sohoapp.home.BaseModel;
import com.soho.sohoapp.home.addproperty.data.PropertyAddress;

public class PortfolioProperty implements BaseModel {
    private PropertyAddress propertyAddress;
    private PortfolioFinance portfolioFinance;
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

    public PortfolioFinance getPortfolioFinance() {
        return portfolioFinance;
    }

    public void setPortfolioFinance(PortfolioFinance portfolioFinance) {
        this.portfolioFinance = portfolioFinance;
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
