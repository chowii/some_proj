package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.sale;

import android.support.annotation.StringRes;

import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.BaseViewInteractable;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public interface SaleAndAuctionSettingsContract {

    interface ViewPresentable {
        void onBackClicked();

        void onSaleOptionClicked();

        void onAuctionOptionClicked();

        void onOnSiteClicked();

        void onOffSiteClicked();

        void onAuctionAddressClicked();

        void onSetTimeClicked();

        void onAppointmentClicked();

        void onInspectionTimeClicked();

        void onPropertySizeClicked();

        void onAuctionAddressSelected(Location location);

        void onAuctionDateClicked();

        void onAuctionTimeClicked();

        void onSaveClicked();

        void onPriceChanged(String price);

        void onDescriptionClicked();

        void onDescriptionChanged(String description);

        void onPropertyPublicStatusUpdated(Property property, boolean verificationCompleted);

        void onInspectionTimesChanged(Property property);

        void onPropertySizeChanged(Property property);
    }

    interface ViewInteractable extends BaseViewInteractable {
        void setPresentable(ViewPresentable presentable);

        Property getPropertyFromExtras();

        void showOptionsForSale();

        void showOptionsForAuction();

        void enableAuctionAddress(boolean enable);

        void enableInspectionTime(boolean enable);

        void showToastMessage(@StringRes int resId);

        void showAuctionAddress(String address);

        void showAuctionDate(String date);

        void showAuctionTime(String time);

        void showDefaultAuctionAddressDesc();

        void showTimePicker(Calendar calendar, TimePickerDialog.OnTimeSetListener listener);

        void showDatePicker(Calendar calendar, DatePickerDialog.OnDateSetListener listener);

        String getListingTitle();

        String getPriceValue();

        boolean isForSaleChecked();

        boolean isOffSiteLocationChecked();

        void selectAuctionOption();

        void showTitle(String title);

        void changePriceValidationIndicator(boolean priceIsValid);

        void showPriceGuide(double value);

        void showDescription(String description);

        void selectOffSiteAuctionOption();

        void showLoadingDialog();

        void hideLoadingDialog();

        void showInspectionTimes(int inspectionTimesQuantity);

        boolean isMaskAddress();

        void showMaskAddress(boolean isMaskAddress);

        void showPropertySize(@StringRes int measurement, int landSize);
    }
}
