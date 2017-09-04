package com.soho.sohoapp.feature.home.portfolio;

import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;

import java.util.List;

public interface PortfolioListContract {

    interface ViewActionsListener {
        void onPullToRefresh();

        void onAddPropertyClicked();

        void onPortfolioClicked(PortfolioCategory portfolioCategory);

        void onNewPropertyCreated();
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        void setData(List<BaseModel> dataList);

        void showError(Throwable throwable);

        void showPullToRefresh();

        void hidePullToRefresh();
    }
}
