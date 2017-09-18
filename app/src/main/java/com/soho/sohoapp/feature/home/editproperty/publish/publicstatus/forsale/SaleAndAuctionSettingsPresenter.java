package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.forsale;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.navigator.RequestCode;

public class SaleAndAuctionSettingsPresenter implements AbsPresenter, SaleAndAuctionSettingsContract.ViewPresentable {
    private final SaleAndAuctionSettingsContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private Property property;

    public SaleAndAuctionSettingsPresenter(SaleAndAuctionSettingsContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        property = view.getPropertyFromExtras();
    }

    @Override
    public void stopPresenting() {
        //todo:
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onSaleOptionClicked() {
        view.showOptionsForSale();
    }

    @Override
    public void onAuctionOptionClicked() {
        view.showOptionsForAuction();
    }

    @Override
    public void onOnSiteClicked() {
        view.enableAuctionAddress(false);
        Location location = property.getLocation();
        if (location != null) {
            view.showAuctionAddress(location.getFullAddress());
        }
    }

    @Override
    public void onOffSiteClicked() {
        view.enableAuctionAddress(true);
    }

    @Override
    public void onAuctionAddressClicked() {
        //todo: open screen with autocomplete address
        navigator.openAutocompleteAddressScreen(RequestCode.PROPERTY_AUCTION_ADDRESS);
    }

    @Override
    public void onSetTimeClicked() {
        view.enableInspectionTime(true);
    }

    @Override
    public void onAppointmentClicked() {
        view.enableInspectionTime(false);
    }

    @Override
    public void onInspectionTimeClicked() {
        // todo: show time picker
    }

    @Override
    public void onPropertySizeClicked() {
        // todo: open Property Size screen
        view.showToast("Coming soon..");
    }
}
