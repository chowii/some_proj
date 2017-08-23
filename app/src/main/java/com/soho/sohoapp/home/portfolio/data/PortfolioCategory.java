package com.soho.sohoapp.home.portfolio.data;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseModel;

public class PortfolioCategory implements BaseModel {
    private String name;
    private int userId;
    private int propertyCount;
    private double estimatedValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPropertyCount() {
        return propertyCount;
    }

    public void setPropertyCount(int propertyCount) {
        this.propertyCount = propertyCount;
    }

    public double getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_owner_portfolio;
    }
}
