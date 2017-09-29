package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.discoverable;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.enums.PropertyStatus;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyFinance;
import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.extensions.StringExtKt;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.utils.Converter;
import com.soho.sohoapp.utils.QueryHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class DiscoverableSettingsPresenter implements AbsPresenter, DiscoverableSettingsContract.ViewPresentable {
    private final DiscoverableSettingsContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private final CompositeDisposable compositeDisposable;
    private Property property;
    private PropertyListing propertyListing;
    private PropertyFinance propertyFinance;

    public DiscoverableSettingsPresenter(DiscoverableSettingsContract.ViewInteractable view, NavigatorInterface navigator) {
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
        view.setReceiveOffersToBuyChecked(propertyListing.getCanReceiveSalesOffers());
        view.setReceiveOffersToLeaseChecked(propertyListing.getCanReceiveRentOffers());

        if (propertyFinance.getEstimatedValue() > 0) {
            view.showEstimatedValue(propertyFinance.getEstimatedValue());
        }
        if (propertyFinance.getEstimatedRent() > 0) {
            view.showEstimatedWeeklyRent(propertyFinance.getEstimatedRent());
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
    public void onSaveClicked() {
        if (isDataValid()) {
            propertyListing.setState(PropertyStatus.DISCOVERABLE);

            propertyListing.setCanReceiveSalesOffers(view.isReceiveOffersToBuyChecked());
            propertyListing.setCanReceiveRentOffers(view.isReceiveOffersToLeaseChecked());
            propertyFinance.setEstimatedValue(StringExtKt.toDoubleOrDefault(view.getEstimatedValue(), 0));
            propertyFinance.setEstimatedRent(StringExtKt.toDoubleOrDefault(view.getEstimatedWeeklyRent(), 0));

            property.setPropertyFinance(propertyFinance);
            property.setPropertyListing(propertyListing);

            sendDataToServer();
        }
    }

    @Override
    public void onReceiveOffersToBuyChanged(boolean isChecked) {
        view.showEstimatedValue(isChecked);
    }

    @Override
    public void onReceiveOffersToLeaseChanged(boolean isChecked) {
        view.showEstimatedWeeklyRent(isChecked);
    }

    @Override
    public void onEstimatedValueChanged(String value) {
        view.changeEstimatedValueIndicator(StringExtKt.toDoubleOrDefault(value, 0) > 0);
    }

    @Override
    public void onEstimatedWeeklyRentChanged(String value) {
        view.changeEstimatedWeeklyRentIndicator(StringExtKt.toDoubleOrDefault(value, 0) > 0);
    }

    @Override
    public void onPropertyPublicStatusUpdated(Property property) {
        navigator.exitWithResultCodeOk(property);
    }

    private boolean isDataValid() {
        boolean dataIsValid = true;
        boolean receiveOffersToBuy = view.isReceiveOffersToBuyChecked();
        boolean receiveOffersToLease = view.isReceiveOffersToLeaseChecked();

        if (!receiveOffersToBuy && !receiveOffersToLease) {
            view.showToastMessage(R.string.publish_property_no_option_selected);
            dataIsValid = false;
        } else if (receiveOffersToBuy && StringExtKt.toDoubleOrDefault(view.getEstimatedValue(), 0) <= 0) {
            view.showToastMessage(R.string.publish_property_empty_estimated_value);
            dataIsValid = false;
        } else if (receiveOffersToLease && StringExtKt.toDoubleOrDefault(view.getEstimatedWeeklyRent(), 0) <= 0) {
            view.showToastMessage(R.string.publish_property_empty_estimated_weekly_rent);
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
