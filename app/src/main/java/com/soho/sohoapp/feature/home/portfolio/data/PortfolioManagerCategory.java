package com.soho.sohoapp.feature.home.portfolio.data;

import android.os.Parcel;

import com.soho.sohoapp.R;

public class PortfolioManagerCategory extends PortfolioCategory {
    private int publicPropertiesCount;

    public static PortfolioManagerCategory fromPortfolioCategory(PortfolioCategory portfolioCategory) {
        PortfolioManagerCategory managerCategory = new PortfolioManagerCategory();
        managerCategory.setName(portfolioCategory.getName());
        managerCategory.setFilterForPortfolio(portfolioCategory.getFilterForPortfolio());
        managerCategory.setUserId(portfolioCategory.getUserId());
        managerCategory.setPropertyCount(portfolioCategory.getPropertyCount());
        managerCategory.setEstimatedValue(portfolioCategory.getEstimatedValue());
        return managerCategory;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_manager_portfolio;
    }

    public int getPublicPropertiesCount() {
        return publicPropertiesCount;
    }

    public void setPublicPropertiesCount(int publicPropertiesCount) {
        this.publicPropertiesCount = publicPropertiesCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.publicPropertiesCount);
    }

    public PortfolioManagerCategory() {
    }

    protected PortfolioManagerCategory(Parcel in) {
        super(in);
        this.publicPropertiesCount = in.readInt();
    }

    public static final Creator<PortfolioManagerCategory> CREATOR = new Creator<PortfolioManagerCategory>() {
        @Override
        public PortfolioManagerCategory createFromParcel(Parcel source) {
            return new PortfolioManagerCategory(source);
        }

        @Override
        public PortfolioManagerCategory[] newArray(int size) {
            return new PortfolioManagerCategory[size];
        }
    };
}
