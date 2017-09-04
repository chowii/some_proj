package com.soho.sohoapp.feature.home.addproperty.investment;

public interface InvestmentContract {

    interface ViewActionsListener {
        void onHomeClicked();

        void onInvestmentClicked();
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        boolean isForOwner();

        void showOwnerQuestion();

        void showAgentQuestion();

        void showOwnerHomeButton();

        void showOwnerInvestmentButton();

        void showAgentHomeButton();

        void showAgentInvestmentButton();

        void sendResultToActivity(boolean isInvestment);
    }
}
