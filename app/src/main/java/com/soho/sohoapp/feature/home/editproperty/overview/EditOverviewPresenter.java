package com.soho.sohoapp.feature.home.editproperty.overview;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.feature.home.editproperty.data.Property;
import com.soho.sohoapp.feature.home.editproperty.data.PropertyStatus;
import com.soho.sohoapp.utils.ColorUtils;

public class EditOverviewPresenter implements AbsPresenter, EditOverviewContract.ViewPresentable {
    private final EditOverviewContract.ViewInteractable view;

    public EditOverviewPresenter(EditOverviewContract.ViewInteractable view) {
        this.view = view;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        Property property = view.getProperty();
        switch (property.getPropertyListing().getState()) {
            case PropertyStatus.PRIVATE:
                view.showMarketplaceState(R.string.edit_property_private);
                view.showMarketplaceStateIndicator(ColorUtils.getPrivatePropertyStateColor());
                view.hideVerificationSection();
                break;
            case PropertyStatus.PUBLIC:
                view.showMarketplaceState(R.string.edit_property_public);
                view.showMarketplaceStateIndicator(ColorUtils.getPublicPropertyStateColor(property.getPropertyVerificationList()));
                view.showVerificationSection();
                break;
        }
        view.setPropertyFinance(property.getPropertyFinance());
    }

    @Override
    public void stopPresenting() {
        //not needed here
    }

    @Override
    public void onMarketplaceStateClicked() {
        view.showToast("Open state settings");
    }

    @Override
    public void onVerificationClicked() {
        view.showToast("Open verification settings");
    }
}
