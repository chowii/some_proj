package com.soho.sohoapp.feature.home.portfolio.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseModel;

public class PortfolioCategory implements BaseModel, Parcelable {
    public static final String FILTER_FAVOURITES = "favourited";
    private String name;
    private String filterForPortfolio;
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

    public String getFilterForPortfolio() {
        return filterForPortfolio;
    }

    public void setFilterForPortfolio(String filterForPortfolio) {
        this.filterForPortfolio = filterForPortfolio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.filterForPortfolio);
        dest.writeInt(this.userId);
        dest.writeInt(this.propertyCount);
        dest.writeDouble(this.estimatedValue);
    }

    public PortfolioCategory() {
    }

    protected PortfolioCategory(Parcel in) {
        this.name = in.readString();
        this.filterForPortfolio = in.readString();
        this.userId = in.readInt();
        this.propertyCount = in.readInt();
        this.estimatedValue = in.readDouble();
    }

    public static final Creator<PortfolioCategory> CREATOR = new Creator<PortfolioCategory>() {
        @Override
        public PortfolioCategory createFromParcel(Parcel in) {
            return new PortfolioCategory(in);
        }

        @Override
        public PortfolioCategory[] newArray(int size) {
            return new PortfolioCategory[size];
        }
    };

}
