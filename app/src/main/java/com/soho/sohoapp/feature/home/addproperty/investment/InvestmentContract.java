package com.soho.sohoapp.feature.home.addproperty.investment;

public interface InvestmentContract {

    interface ViewPresentable {
        void onHomeClicked();

        void onInvestmentClicked();
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

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
