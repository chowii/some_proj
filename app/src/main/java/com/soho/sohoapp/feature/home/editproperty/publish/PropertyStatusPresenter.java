package com.soho.sohoapp.feature.home.editproperty.publish;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.navigator.RequestCode;

public class PropertyStatusPresenter implements AbsPresenter, PropertyStatusContract.ViewPresentable {
    private final PropertyStatusContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private Property property;

    public PropertyStatusPresenter(PropertyStatusContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);

        property = view.getPropertyFromExtras();
        PropertyListing propertyListing = property.getPropertyListing();
        if (propertyListing.isPrivate()) {
            view.hidePublicIndicator();
            view.showPrivateIndicator();
        } else if (propertyListing.isPublic()) {
            view.hidePrivateIndicator();
            view.showPublicIndicator();
        }
    }

    @Override
    public void stopPresenting() {
        //not needed here
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onPublicClicked() {
        navigator.openPublicStatusSettingsScreen(property, RequestCode.PROPERTY_STATUS_UPDATE);
    }

    @Override
    public void onPrivateClicked() {
        navigator.openPrivateStatusSettingsScreen(property, RequestCode.PROPERTY_STATUS_UPDATE);
    }

    @Override
    public void onPropertyStatusUpdated(Property property) {
        navigator.exitWithResultCodeOk(property);
    }
}
