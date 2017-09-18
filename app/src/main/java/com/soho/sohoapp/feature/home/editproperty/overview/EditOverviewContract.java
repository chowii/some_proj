package com.soho.sohoapp.feature.home.editproperty.overview;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.data.models.PropertyFinance;

public interface EditOverviewContract {

    interface ViewPresentable {
        void onMarketplaceStateClicked();

        void onVerificationClicked();

        void onPropertyStatusUpdated(PropertyListing propertyListing);
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
    }
}
