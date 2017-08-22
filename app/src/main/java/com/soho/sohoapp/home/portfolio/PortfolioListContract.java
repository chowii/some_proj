package com.soho.sohoapp.home.portfolio;

public interface PortfolioListContract {

    interface ViewActionsListener {
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);
    }
}
