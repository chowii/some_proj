package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus;

import com.soho.sohoapp.feature.home.editproperty.data.Property;

public interface PublicStatusSettingsContract {

    interface ViewPresentable {
        void onBackClicked();

        void onDiscoverableClicked();

        void onSaleAndAuctionClicked();

        void onRentClicked();
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showDiscoverableIndicator();

        void hideDiscoverableIndicator();

        void showSaleAndAuctionIndicator();

        void hideSaleAndAuctionIndicator();

        void showRentIndicator();

        void hideRentIndicator();

        Property getPropertyFromExtras();
    }
}
