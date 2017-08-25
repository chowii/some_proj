package com.soho.sohoapp.home.portfolio.details;

import com.soho.sohoapp.home.BaseModel;
import com.soho.sohoapp.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.home.portfolio.data.PortfolioManagerCategory;

import java.util.List;

public interface PortfolioDetailsContract {

    interface ViewActionsListener {
        void onBackClicked();

        void onAddPropertyClicked();

        void onPullToRefresh();

        void onNewPropertyCreated();
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        void showPullToRefresh();

        void hidePullToRefresh();

        boolean isOwnerScreen();

        void showLoadingError();

        void setData(List<BaseModel> dataList);

        PortfolioCategory getOwnerPortfolio();

        PortfolioManagerCategory getManagerPortfolio();

    }
}
