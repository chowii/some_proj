package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.forsale;


import com.soho.sohoapp.data.models.Property;

public interface SaleAndAuctionSettingsContract {

    interface ViewPresentable {
        void onBackClicked();

        void onSaleOptionClicked();

        void onAuctionOptionClicked();

        void onOnSiteClicked();

        void onOffSiteClicked();

        void onAuctionAddressClicked();

        void onSetTimeClicked();

        void onAppointmentClicked();

        void onInspectionTimeClicked();

        void onPropertySizeClicked();
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        Property getPropertyFromExtras();

        void showOptionsForSale();

        void showOptionsForAuction();

        void enableAuctionAddress(boolean enable);

        void enableInspectionTime(boolean enable);

        void showToast(String message);

        void showAuctionAddress(String address);
    }
}
