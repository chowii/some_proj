package com.soho.sohoapp.feature.home.portfolio;

import com.soho.sohoapp.feature.BaseViewInteractable;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;

import java.util.List;

public interface PortfolioListContract {

    interface ViewPresentable {
        void onPullToRefresh();

        void onAddPropertyClicked();

        void onPortfolioClicked(PortfolioCategory portfolioCategory);

        void onNewPropertyCreated();
    }

    interface ViewInteractable extends BaseViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void setData(List<BaseModel> dataList);

        void showPullToRefresh();

        void hidePullToRefresh();
    }
}
