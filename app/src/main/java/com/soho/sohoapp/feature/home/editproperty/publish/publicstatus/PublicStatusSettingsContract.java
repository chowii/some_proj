package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus;

import com.soho.sohoapp.data.models.Property;

public interface PublicStatusSettingsContract {

    interface ViewPresentable {
        void onBackClicked();

        void onDiscoverableClicked();

        void onSaleAndAuctionClicked();

        void onRentClicked();

        void onPropertyStatusUpdated(Property property, boolean verificationCompleted);
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
