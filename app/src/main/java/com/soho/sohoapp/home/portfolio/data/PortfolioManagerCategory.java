package com.soho.sohoapp.home.portfolio.data;

import com.soho.sohoapp.R;

public class PortfolioManagerCategory extends PortfolioCategory {
    private int publicPropertiesCount;

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
}
