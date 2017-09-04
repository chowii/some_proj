package com.soho.sohoapp.feature.home.portfolio.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;
import com.soho.sohoapp.feature.home.editproperty.data.PropertyImage;

import java.util.ArrayList;
import java.util.List;

public class PortfolioProperty implements BaseModel, Parcelable {
    private List<PropertyImage> propertyImageList;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.propertyImageList);
        dest.writeParcelable(this.propertyAddress, flags);
        dest.writeParcelable(this.portfolioFinance, flags);
        dest.writeInt(this.viewType);
        dest.writeInt(this.id);
        dest.writeString(this.state);
    }

    public PortfolioProperty() {
    }

    protected PortfolioProperty(Parcel in) {
        this.propertyImageList = new ArrayList<>();
        in.readList(this.propertyImageList, PropertyImage.class.getClassLoader());
        this.propertyAddress = in.readParcelable(PropertyAddress.class.getClassLoader());
        this.portfolioFinance = in.readParcelable(PortfolioFinance.class.getClassLoader());
        this.viewType = in.readInt();
        this.id = in.readInt();
        this.state = in.readString();
    }

    public static final Creator<PortfolioProperty> CREATOR = new Creator<PortfolioProperty>() {
        @Override
        public PortfolioProperty createFromParcel(Parcel source) {
            return new PortfolioProperty(source);
        }

        @Override
        public PortfolioProperty[] newArray(int size) {
            return new PortfolioProperty[size];
        }
    };
}
