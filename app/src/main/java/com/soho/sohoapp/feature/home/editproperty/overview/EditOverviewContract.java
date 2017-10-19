package com.soho.sohoapp.feature.home.editproperty.overview;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.PickerItem;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyFinance;
import com.soho.sohoapp.data.models.Verification;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;

import java.util.List;

public interface EditOverviewContract {

    interface ViewPresentable {
        void onMarketplaceStateClicked();

        void onVerificationClicked();

        void onPropertyStatusUpdated(Property property, boolean verificationCompleted);

        void onPropertyVerificationsUpdated(List<Verification> verifications);

        void onAddressClicked();

        void onPropertyAddressChanged(Location location);

        void onMaskAddressChanged(boolean isChecked);

        void onRoomsNumberChanged(double bedrooms, double bathrooms, double carspots);

        void onPropertyTypeChanged(PickerItem pickerItem);

        void onRenovationClicked();

        void onRenovationChanged(String renovation);

        void onPortfolioClicked();

        void onHomeClicked();

        void onInvestmentClicked();

        void onArchiveClicked();

        void onArchiveConfirmed();

        void onPropertyFinanceChanged(PropertyFinance finance);
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

        void notifyActivityAboutAddressChanges(Location location);

        void notifyActivityAboutRoomsChanges(double bedrooms, double bathrooms, double carspots);

        void notifyActivityAboutPropertyTypeChanged(String type);

        void notifyActivityAboutRenovationChanged(String renovation);

        void notifyActivityAboutInvestmentStatusChanged(boolean isInvestment);

        void notifyActivityAboutPropertyStatusChanged(String status);

        void notifyActivityAboutPropertyFinanceChanged(PropertyFinance finance);

        void showMaskAddress(boolean isMaskAddress);

        void showRoomsNumber(double bedrooms, double bathrooms, double carspots);

        List<PropertyType> getPropertyTypesFromExtras();

        void initPropertyTypes(List<PickerItem> pickerItems, int currentType);

        void showRenovation(String renovation);

        void showPortfolioTypesDialog();

        void showPortfolioTypes(@StringRes int portfolioType);
    }
}
