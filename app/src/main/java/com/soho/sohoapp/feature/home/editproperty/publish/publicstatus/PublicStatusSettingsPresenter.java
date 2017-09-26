package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.enums.PropertyStatus;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.navigator.RequestCode;

public class PublicStatusSettingsPresenter implements AbsPresenter, PublicStatusSettingsContract.ViewPresentable {
    private final PublicStatusSettingsContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private Property property;

    public PublicStatusSettingsPresenter(PublicStatusSettingsContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);

        property = view.getPropertyFromExtras();
        PropertyListing propertyListing = property.getPropertyListing();
        if (propertyListing.isPrivate()) {
            view.hideDiscoverableIndicator();
            view.hideSaleAndAuctionIndicator();
            view.hideRentIndicator();
        } else if (propertyListing.isPublic()) {
            switch (propertyListing.getState()) {
                case PropertyStatus.DISCOVERABLE:
                    view.hideSaleAndAuctionIndicator();
                    view.hideRentIndicator();
                    view.showDiscoverableIndicator();
                    break;
                case PropertyStatus.SALE:
                case PropertyStatus.AUCTION:
                    view.hideDiscoverableIndicator();
                    view.hideRentIndicator();
                    view.showSaleAndAuctionIndicator();
                    break;
                case PropertyStatus.RENT:
                    view.hideDiscoverableIndicator();
                    view.hideSaleAndAuctionIndicator();
                    view.showRentIndicator();
                    break;
            }
        }
    }

    @Override
    public void stopPresenting() {
        // not needed here
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onDiscoverableClicked() {
        //todo: open Discoverable screen
    }

    @Override
    public void onSaleAndAuctionClicked() {
        navigator.openSaleAndAuctionSettingsScreen(property, RequestCode.PROPERTY_PUBLIC_STATUS_UPDATE);
    }

    @Override
    public void onRentClicked() {
        navigator.openRentSettingsScreen(property, RequestCode.PROPERTY_PUBLIC_STATUS_UPDATE);
    }

    @Override
    public void onPropertyStatusUpdated(Property property) {
        navigator.exitWithResultCodeOk(property);
    }
}
