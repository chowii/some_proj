package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.discoverable;

import android.support.annotation.StringRes;

import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.BaseViewInteractable;

public interface DiscoverableSettingsContract {

    interface ViewPresentable {
        void onBackClicked();

        void onSaveClicked();

        void onReceiveOffersToBuyChanged(boolean isChecked);

        void onReceiveOffersToLeaseChanged(boolean isChecked);

        void onEstimatedValueChanged(String value);

        void onEstimatedWeeklyRentChanged(String value);

        void onPropertyPublicStatusUpdated(Property property);
    }

    interface ViewInteractable extends BaseViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showEstimatedValue(boolean visible);

        void showEstimatedValue(double value);

        void showEstimatedWeeklyRent(boolean visible);

        void showEstimatedWeeklyRent(double value);

        void changeEstimatedValueIndicator(boolean isValid);

        void changeEstimatedWeeklyRentIndicator(boolean isValid);

        void showToastMessage(@StringRes int resId);

        void setReceiveOffersToBuyChecked(boolean checked);

        void setReceiveOffersToLeaseChecked(boolean checked);

        void showLoadingDialog();

        void hideLoadingDialog();

        boolean isReceiveOffersToBuyChecked();

        boolean isReceiveOffersToLeaseChecked();

        Property getPropertyFromExtras();

        String getEstimatedValue();

        String getEstimatedWeeklyRent();
    }
}
