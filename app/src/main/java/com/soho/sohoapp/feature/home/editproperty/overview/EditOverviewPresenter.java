package com.soho.sohoapp.feature.home.editproperty.overview;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.utils.ColorUtils;

public class EditOverviewPresenter implements AbsPresenter, EditOverviewContract.ViewPresentable {
    private final EditOverviewContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private Property property;

    public EditOverviewPresenter(EditOverviewContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        property = view.getProperty();
        initPropertyListing(property.getPropertyListing());
        view.setPropertyFinance(property.getPropertyFinance());
    }

    @Override
    public void stopPresenting() {
        //not needed here
    }

    @Override
    public void onMarketplaceStateClicked() {
        navigator.openPropertyStatusScreen(property, RequestCode.EDIT_PROPERTY_STATUS_UPDATE);
    }

    @Override
    public void onVerificationClicked() {
        view.showToast("Open verification settings");
    }

    @Override
    public void onPropertyStatusUpdated(Property property) {
        this.property = property;
        initPropertyListing(property.getPropertyListing());
        //todo: update also property description
    }

    private void initPropertyListing(PropertyListing propertyListing) {
        if (propertyListing.isPrivate()) {
            view.showMarketplaceState(R.string.edit_property_private);
            view.showMarketplaceStateIndicator(ColorUtils.getPrivatePropertyStateColor());
            view.hideVerificationSection();
        } else if (propertyListing.isPublic()) {
            view.showMarketplaceState(R.string.edit_property_public);
            view.showMarketplaceStateIndicator(ColorUtils.getPublicPropertyStateColor(property.getVerifications()));
            view.showVerificationSection();
        } else if (propertyListing.isArchived()) {
            //todo: show label "Archived" and make it disabled
        }
    }

}
