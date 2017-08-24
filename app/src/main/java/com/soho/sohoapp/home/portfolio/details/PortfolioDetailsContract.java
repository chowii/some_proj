package com.soho.sohoapp.home.portfolio.details;

import com.soho.sohoapp.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.home.portfolio.data.PortfolioManagerCategory;

public interface PortfolioDetailsContract {
    interface ViewActionsListener {
        void onBackClicked();
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        boolean isOwnerScreen();

        PortfolioCategory getOwnerPortfolio();

        PortfolioManagerCategory getManagerPortfolio();
    }
}
