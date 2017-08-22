package com.soho.sohoapp.home.portfolio.data;

public class PortfolioCategory {
    private String name;
    private int userId;
    private int propertyCount;
    private double estimatedValue;
    private int publicPropertiesCount;

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

    public int getPublicPropertiesCount() {
        return publicPropertiesCount;
    }

    public void setPublicPropertiesCount(int publicPropertiesCount) {
        this.publicPropertiesCount = publicPropertiesCount;
    }
}
