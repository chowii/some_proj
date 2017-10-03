package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.updated;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.enums.PropertyStatus;
import com.soho.sohoapp.data.enums.VerificationStatus;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.Verification;
import com.soho.sohoapp.navigator.NavigatorInterface;

import java.util.List;

public class PropertyStatusUpdatedPresenter implements AbsPresenter, PropertyStatusUpdatedContract.ViewPresentable {
    private final PropertyStatusUpdatedContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private Property property;

    public PropertyStatusUpdatedPresenter(PropertyStatusUpdatedContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        property = view.getPropertyFromExtras();
        view.showAddress(property.getLocationSafe().getFullAddress());

        if (hasNotVerifiedItem(property.getVerificationsSafe())) {
            view.showNextVerificationButton();
        }

        String propertyStatus = property.getPropertyListingSafe().getState();
        if (propertyStatus != null) {
            switch (propertyStatus) {
                case PropertyStatus.DISCOVERABLE:
                    view.showStatus(R.string.publish_property_status_discoverable);
                    break;
                case PropertyStatus.SALE:
                    view.showStatus(R.string.publish_property_status_for_sale);
                    break;
                case PropertyStatus.AUCTION:
                    view.showStatus(R.string.publish_property_status_for_auction);
                    break;
                case PropertyStatus.RENT:
                    view.showStatus(R.string.publish_property_status_for_rent);
                    break;
            }
        }
    }

    @Override
    public void stopPresenting() {
        //not needed here
    }

    @Override
    public void onNextClicked() {
        navigator.exitWithResultCodeOk(property, true);
    }

    @Override
    public void onNextVerificationClicked() {
        navigator.exitWithResultCodeOk(property, false);
    }

    private boolean hasNotVerifiedItem(List<Verification> verifications) {
        for (Verification verification : verifications) {
            if (!VerificationStatus.VERIFIED.equals(verification.getState())) {
                return true;
            }
        }
        return false;
    }
}
