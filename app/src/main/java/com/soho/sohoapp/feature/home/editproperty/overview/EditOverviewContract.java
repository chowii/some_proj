package com.soho.sohoapp.feature.home.editproperty.overview;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyFinance;

public interface EditOverviewContract {

    interface ViewPresentable {
        void onMarketplaceStateClicked();

        void onVerificationClicked();

        void onPropertyStatusUpdated(Property property, boolean verificationCompleted);

        void onAddressClicked();

        void onPropertyAddressChanged(Location location);

        void onMaskAddressChanged(boolean isChecked);
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        Property getProperty();

        void showMarketplaceState(@StringRes int state);

        void showToast(String message);

        void showMarketplaceStateIndicator(@ColorRes int color);

        void showVerificationSection();

        void hideVerificationSection();

        void setPropertyFinance(PropertyFinance finance);

        void showPropertyAddress(String address);

        void notifyActivityAboutChanges(Location location);

        void showMaskAddress(boolean isMaskAddress);
    }
}
