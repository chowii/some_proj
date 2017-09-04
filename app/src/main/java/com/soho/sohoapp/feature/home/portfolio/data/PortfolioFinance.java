package com.soho.sohoapp.feature.home.portfolio.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PortfolioFinance implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.purchasePrice);
        dest.writeDouble(this.loanAmount);
        dest.writeDouble(this.estimatedValue);
        dest.writeByte(this.isRented ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.actualRent);
        dest.writeDouble(this.estimatedRent);
    }

    public PortfolioFinance() {
    }

    protected PortfolioFinance(Parcel in) {
        this.id = in.readInt();
        this.purchasePrice = in.readDouble();
        this.loanAmount = in.readDouble();
        this.estimatedValue = in.readDouble();
        this.isRented = in.readByte() != 0;
        this.actualRent = in.readDouble();
        this.estimatedRent = in.readDouble();
    }

    public static final Creator<PortfolioFinance> CREATOR = new Creator<PortfolioFinance>() {
        @Override
        public PortfolioFinance createFromParcel(Parcel source) {
            return new PortfolioFinance(source);
        }

        @Override
        public PortfolioFinance[] newArray(int size) {
            return new PortfolioFinance[size];
        }
    };
}
