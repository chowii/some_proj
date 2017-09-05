package com.soho.sohoapp.feature.home.portfolio.details;

import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;

import java.util.List;

public interface PortfolioDetailsContract {

    interface ViewPresentable {
        void onBackClicked();

        void onAddPropertyClicked();

        void onPullToRefresh();

        void onNewPropertyCreated();

        void onOwnerPropertyClicked(PortfolioProperty property);
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showPullToRefresh();

        void hidePullToRefresh();

        boolean isOwnerScreen();

        void showLoadingError();

        void setData(List<BaseModel> dataList);

        PortfolioCategory getOwnerPortfolio();

        PortfolioManagerCategory getManagerPortfolio();

    }
}
