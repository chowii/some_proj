package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.sale;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.enums.PropertyStatus;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyFinance;
import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.extensions.LongExtKt;
import com.soho.sohoapp.extensions.StringExtKt;
import com.soho.sohoapp.logger.Logger;
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

public class SaleAndAuctionSettingsPresenter implements AbsPresenter, SaleAndAuctionSettingsContract.ViewPresentable {
    private final SaleAndAuctionSettingsContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private final Logger logger;
    private final CompositeDisposable compositeDisposable;
    private Property property;
    private PropertyListing propertyListing;
    private PropertyFinance propertyFinance;
    private Calendar auctionDateCalendar;
    private Calendar auctionTimeCalendar;

    public SaleAndAuctionSettingsPresenter(SaleAndAuctionSettingsContract.ViewInteractable view,
                                           NavigatorInterface navigator,
                                           Logger logger) {
        this.view = view;
        this.navigator = navigator;
        this.logger = logger;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        property = view.getPropertyFromExtras();

        propertyListing = property.getPropertyListingSafe();
        propertyFinance = property.getPropertyFinanceSafe();

        Location location = property.getLocation();
        if (location != null) {
            view.showAuctionAddress(location.getFullAddress());
        }

        view.showInspectionTimes(propertyListing.getInspectionTimesSafe().size());
        view.showMaskAddress(property.getLocationSafe().getMaskAddress());

        //init view
        if (PropertyStatus.AUCTION.equals(propertyListing.getState())) {
            view.showTitle(propertyListing.getAuctionTitle());
            view.showOptionsForAuction();
            view.selectAuctionOption();

            initAuctionDate();
            initAuctionLocation();
        } else if (PropertyStatus.SALE.equals(propertyListing.getState())) {
            view.showTitle(propertyListing.getSaleTitle());
            view.showOptionsForSale();
        }

        if (propertyFinance.getEstimatedValue() > 0) {
            view.showPriceGuide(propertyFinance.getEstimatedValue());
        }

        String description = property.getDescription();
        if (description != null && !description.isEmpty()) {
            view.showDescription(description);
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
    public void onSaleOptionClicked() {
        view.showOptionsForSale();
    }

    @Override
    public void onAuctionOptionClicked() {
        view.showOptionsForAuction();
    }

    @Override
    public void onOnSiteClicked() {
        view.enableAuctionAddress(false);
        propertyListing.setOnSiteAuction(true);
        Location location = property.getLocation();
        if (location != null) {
            view.showAuctionAddress(location.getFullAddress());
        }
    }

    @Override
    public void onOffSiteClicked() {
        view.enableAuctionAddress(true);
        propertyListing.setOnSiteAuction(false);
        Location offSiteLocation = propertyListing.getOffSiteLocation();
        if (offSiteLocation != null) {
            view.showAuctionAddress(offSiteLocation.getFullAddress());
        } else {
            view.showDefaultAuctionAddressDesc();
        }
    }

    @Override
    public void onAuctionAddressClicked() {
        navigator.openAutocompleteAddressScreen(RequestCode.PROPERTY_AUCTION_ADDRESS);
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
    public void onInspectionTimeClicked() {
        navigator.openInspectionTimeScreen(property, RequestCode.SALE_SETTINGS_INSPECTION_TIME);
    }

    @Override
    public void onPropertySizeClicked() {
        // todo: open Property Size screen
        view.showToastMessage(R.string.coming_soon);
    }

    @Override
    public void onAuctionAddressSelected(Location location) {
        propertyListing.setOffSiteLocation(location);
        view.showAuctionAddress(location.getFullAddress());
    }

    @Override
    public void onAuctionDateClicked() {
        Calendar calendar = auctionDateCalendar != null ? auctionDateCalendar : Calendar.getInstance();
        view.showDatePicker(calendar, (pickerView, year, monthOfYear, dayOfMonth) -> {
            if (auctionDateCalendar == null) {
                auctionDateCalendar = Calendar.getInstance();
            }
            auctionDateCalendar.set(Calendar.YEAR, year);
            auctionDateCalendar.set(Calendar.MONTH, monthOfYear);
            auctionDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            view.showAuctionDate(LongExtKt.toStringWithDisplayFormat(auctionDateCalendar.getTimeInMillis()));
        });
    }

    @Override
    public void onAuctionTimeClicked() {
        Calendar calendar = auctionTimeCalendar != null ? auctionTimeCalendar : Calendar.getInstance();
        view.showTimePicker(calendar, (pickerView, hourOfDay, minute, second) -> {
            if (auctionTimeCalendar == null) {
                auctionTimeCalendar = Calendar.getInstance();
            }
            auctionTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            auctionTimeCalendar.set(Calendar.MINUTE, minute);
            auctionTimeCalendar.set(Calendar.SECOND, second);
            view.showAuctionTime(LongExtKt.toStringWithTimeFormat(auctionTimeCalendar.getTimeInMillis()));
        });
    }

    @Override
    public void onPriceChanged(String price) {
        view.changePriceValidationIndicator(StringExtKt.toDoubleOrDefault(price, 0) > 0);
    }

    @Override
    public void onDescriptionClicked() {
        navigator.openPropertyDescriptionScreen(property.getDescription(), RequestCode.PROPERTY_SALE_SETTINGS_DESCRIPTION);
    }

    @Override
    public void onDescriptionChanged(String description) {
        view.showDescription(description);
        property.setDescription(description);
    }

    @Override
    public void onPropertyPublicStatusUpdated(Property property, boolean verificationCompleted) {
        navigator.exitWithResultCodeOk(property, verificationCompleted);
    }

    @Override
    public void onInspectionTimesChanged(Property property) {
        this.property = property;
        view.showInspectionTimes(property.getPropertyListingSafe().getInspectionTimesSafe().size());
    }

    @Override
    public void onSaveClicked() {
        if (isDataValid()) {
            if (view.isForSaleChecked()) {
                propertyListing.setState(PropertyStatus.SALE);
                propertyListing.setSaleTitle(view.getListingTitle());
            } else {
                propertyListing.setState(PropertyStatus.AUCTION);
                propertyListing.setAuctionTitle(view.getListingTitle());
                auctionDateCalendar.set(Calendar.HOUR_OF_DAY, auctionTimeCalendar.get(Calendar.HOUR_OF_DAY));
                auctionDateCalendar.set(Calendar.MINUTE, auctionTimeCalendar.get(Calendar.MINUTE));
                auctionDateCalendar.set(Calendar.SECOND, auctionTimeCalendar.get(Calendar.SECOND));
                propertyListing.setAuctionTime(auctionDateCalendar.getTimeInMillis());
            }

            propertyFinance.setEstimatedValue(StringExtKt.toDoubleOrDefault(view.getPriceValue(), 0));
            property.setPropertyFinance(propertyFinance);
            property.setPropertyListing(propertyListing);
            property.getLocationSafe().setMaskAddress(view.isMaskAddress());

            sendDataToServer();
        }
    }

    private boolean isDataValid() {
        boolean dataIsValid = true;
        if (view.getListingTitle().isEmpty()) {
            view.showToastMessage(R.string.publish_property_not_valid_title);
            dataIsValid = false;
        } else if (StringExtKt.toDoubleOrDefault(view.getPriceValue(), 0) <= 0) {
            view.showToastMessage(R.string.publish_property_not_valid_price);
            dataIsValid = false;
        } else if (!view.isForSaleChecked() && view.isOffSiteLocationChecked() && propertyListing.getOffSiteLocation() == null) {
            view.showToastMessage(R.string.publish_property_not_valid_off_site_location);
            dataIsValid = false;
        } else if (!view.isForSaleChecked() && auctionDateCalendar == null) {
            view.showToastMessage(R.string.publish_property_not_valid_auction_date);
            dataIsValid = false;
        } else if (!view.isForSaleChecked() && auctionTimeCalendar == null) {
            view.showToastMessage(R.string.publish_property_not_valid_auction_time);
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
                    logger.e("Error during property publishing", throwable);
                }));
    }

    private void initAuctionLocation() {
        if (propertyListing.isOnSiteAuction()) {
            Location location = property.getLocation();
            if (location != null) {
                view.showAuctionAddress(location.getFullAddress());
            }
        } else {
            view.selectOffSiteAuctionOption();
            Location offSiteLocation = propertyListing.getOffSiteLocation();
            if (offSiteLocation != null) {
                view.showAuctionAddress(offSiteLocation.getFullAddress());
            }
        }
    }

    private void initAuctionDate() {
        Long auctionTime = propertyListing.getAuctionTime();
        if (auctionTime != null) {
            auctionTimeCalendar = Calendar.getInstance();
            auctionDateCalendar = Calendar.getInstance();
            auctionTimeCalendar.setTime(new Date(auctionTime));
            auctionDateCalendar.setTime(new Date(auctionTime));
            view.showAuctionTime(LongExtKt.toStringWithTimeFormat(auctionTime));
            view.showAuctionDate(LongExtKt.toStringWithDisplayFormat(auctionTime));
        }
    }
}
