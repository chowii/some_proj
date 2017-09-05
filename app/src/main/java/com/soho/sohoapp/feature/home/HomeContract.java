package com.soho.sohoapp.feature.home;

public interface HomeContract {

    interface ViewPresentable {
        void onMarketplaceClicked();

        void onManageClicked();

        void onOffersClicked();

        void onMoreClicked();

        void onAddPropertyClicked();
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showMarketplaceTab();

        void showManageTab();

        void showOffersTab();

        void showMoreTab();
    }
}
