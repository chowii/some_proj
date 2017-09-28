package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.rent;

import android.support.annotation.StringRes;

import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.BaseViewInteractable;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public interface RentSettingsContract {

    interface ViewPresentable {
        void onBackClicked();

        void onSetTimeClicked();

        void onAppointmentClicked();

        void onDescriptionClicked();

        void onDescriptionUpdated(String description);

        void onPriceChanged(String price);

        void onAvailabilityClicked();

        void onInspectionTimeClicked();

        void onPropertySizeClicked();

        void onSaveClicked();
    }

    interface ViewInteractable extends BaseViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void enableInspectionTime(boolean enable);

        void showDescription(String description);

        void changePriceValidationIndicator(boolean priceIsValid);

        void showDatePicker(Calendar calendar, DatePickerDialog.OnDateSetListener listener);

        void showAvailableFrom(String date);

        void showToastMessage(@StringRes int resId);

        void showPriceGuide(double value);

        void showTitle(String title);

        boolean isWeeklyPaymentChecked();

        void showLoadingDialog();

        void hideLoadingDialog();

        void selectMonthlyRentOption();

        Property getPropertyFromExtras();

        String getRentTitle();

        String getRentalPriceValue();

    }
}
