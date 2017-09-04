package com.soho.sohoapp.feature.home.portfolio.data;

public class PortfolioFinance {
    private int id;
    private double purchasePrice;
    private double loanAmount;
    private double estimatedValue;
    private boolean isRented;
    private double actualRent;
    private double estimatedRent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public double getActualRent() {
        return actualRent;
    }

    public void setActualRent(double actualRent) {
        this.actualRent = actualRent;
    }

    public double getEstimatedRent() {
        return estimatedRent;
    }

    public void setEstimatedRent(double estimatedRent) {
        this.estimatedRent = estimatedRent;
    }

}
