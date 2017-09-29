package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.rent;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.enums.PropertyStatus;
import com.soho.sohoapp.data.enums.RentPaymentFrequency;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyFinance;
import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.extensions.LongExtKt;
import com.soho.sohoapp.extensions.StringExtKt;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.utils.Converter;
import com.soho.sohoapp.utils.QueryHashMap;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class RentSettingsPresenter implements AbsPresenter, RentSettingsContract.ViewPresentable {
    private final RentSettingsContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private final CompositeDisposable compositeDisposable;
    private Property property;
    private PropertyListing propertyListing;
    private PropertyFinance propertyFinance;
    private Calendar availabilityCalendar;

    public RentSettingsPresenter(RentSettingsContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        property = view.getPropertyFromExtras();

        propertyListing = property.getPropertyListingSafe();
        propertyFinance = property.getPropertyFinanceSafe();

        //init view
        if (PropertyStatus.RENT.equals(propertyListing.getState())) {
            view.showTitle(propertyListing.getRentTitle());
        }
        if (RentPaymentFrequency.MONTHLY.equals(propertyListing.getRentPaymentFrequency())) {
            view.selectMonthlyRentOption();
        }
        if (propertyFinance.getEstimatedValue() > 0) {
            view.showPriceGuide(propertyFinance.getEstimatedValue());
        }

        String description = property.getDescription();
        if (description != null && !description.isEmpty()) {
            view.showDescription(description);
        }

        Long availableFrom = propertyListing.getAvailableFrom();
        if (availableFrom != null) {
            availabilityCalendar = Calendar.getInstance();
            availabilityCalendar.setTime(new Date(availableFrom));
            view.showAvailableFrom(LongExtKt.toStringWithDisplayFormat(availableFrom));
        }
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onSetTimeClicked() {
        view.enableInspectionTime(true);
    }

    @Override
    public void onAppointmentClicked() {
        view.enableInspectionTime(false);
    }

    @Override
    public void onDescriptionClicked() {
        navigator.openPropertyDescriptionScreen(property.getDescription(), RequestCode.PROPERTY_RENT_SETTINGS_DESCRIPTION);
    }

    @Override
    public void onDescriptionUpdated(String description) {
        view.showDescription(description);
        property.setDescription(description);
    }

    @Override
    public void onPriceChanged(String price) {
        view.changePriceValidationIndicator(StringExtKt.toDoubleOrDefault(price, 0) > 0);
    }

    @Override
    public void onAvailabilityClicked() {
        Calendar calendar = availabilityCalendar != null ? availabilityCalendar : Calendar.getInstance();
        view.showDatePicker(calendar, (pickerView, year, monthOfYear, dayOfMonth) -> {
            if (availabilityCalendar == null) {
                availabilityCalendar = Calendar.getInstance();
            }
            availabilityCalendar.set(Calendar.YEAR, year);
            availabilityCalendar.set(Calendar.MONTH, monthOfYear);
            availabilityCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            view.showAvailableFrom(LongExtKt.toStringWithDisplayFormat(availabilityCalendar.getTimeInMillis()));
        });
    }

    @Override
    public void onInspectionTimeClicked() {
        view.showToastMessage(R.string.coming_soon);
    }

    @Override
    public void onPropertySizeClicked() {
        view.showToastMessage(R.string.coming_soon);
    }

    @Override
    public void onSaveClicked() {
        if (isDataValid()) {
            if (view.isWeeklyPaymentChecked()) {
                propertyListing.setRentPaymentFrequency(RentPaymentFrequency.WEEKLY);
            } else {
                propertyListing.setRentPaymentFrequency(RentPaymentFrequency.MONTHLY);
            }
            propertyListing.setState(PropertyStatus.RENT);
            propertyListing.setRentTitle(view.getRentTitle());

            if (availabilityCalendar != null) {
                propertyListing.setAvailableFrom(availabilityCalendar.getTimeInMillis());
            }

            propertyFinance.setEstimatedValue(StringExtKt.toDoubleOrDefault(view.getRentalPriceValue(), 0));
            property.setPropertyFinance(propertyFinance);
            property.setPropertyListing(propertyListing);

            sendDataToServer();
        }
    }

    @Override
    public void onPropertyPublicStatusUpdated(Property property) {
        navigator.exitWithResultCodeOk(property);
    }

    private boolean isDataValid() {
        boolean dataIsValid = true;
        if (view.getRentTitle().isEmpty()) {
            view.showToastMessage(R.string.publish_property_not_valid_title);
            dataIsValid = false;
        } else if (StringExtKt.toDoubleOrDefault(view.getRentalPriceValue(), 0) <= 0) {
            view.showToastMessage(R.string.publish_property_not_valid_price);
            dataIsValid = false;
        }
        return dataIsValid;
    }

    private void sendDataToServer() {
        view.showLoadingDialog();
        QueryHashMap listingMap = Converter.toMap(propertyListing);
        compositeDisposable.add(DEPENDENCIES.getSohoService().updatePropertyListing(property.getId(), listingMap)
                .switchMap(propertyListingResult ->
                {
                    QueryHashMap propertyMap = Converter.toMap(property);
                    return DEPENDENCIES.getSohoService().updateProperty(property.getId(), propertyMap);
                })
                .map(Converter::toProperty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(property ->
                {
                    view.hideLoadingDialog();
                    navigator.openPropertyStatusUpdatedScreen(property, RequestCode.PROPERTY_PUBLIC_STATUS_UPDATED);
                }, throwable ->
                {
                    view.showError(throwable);
                    view.hideLoadingDialog();
                }));
    }
}
