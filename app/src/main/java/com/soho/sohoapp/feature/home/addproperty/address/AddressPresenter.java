package com.soho.sohoapp.feature.home.addproperty.address;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;
import com.soho.sohoapp.location.LocationProvider;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class AddressPresenter implements AbsPresenter, AddressContract.ViewPresentable {
    private final AddressContract.ViewInteractable view;
    private final LocationProvider locationProvider;
    private final CompositeSubscription compositeSubscription;
    private PropertyAddress propertyAddress;

    public AddressPresenter(AddressContract.ViewInteractable view, LocationProvider locationProvider) {
        this.view = view;
        this.locationProvider = locationProvider;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        view.showKeyboard();
    }

    @Override
    public void stopPresenting() {
        view.hideKeyboard();
        compositeSubscription.unsubscribe();
    }

    @Override
    public void onAddressClicked(String placeId, String fullAddress) {
        view.showLoadingDialog();
        compositeSubscription.add(locationProvider.getLatLng(placeId)
                .flatMap(latLng -> locationProvider.getAddress(latLng, fullAddress))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(propertyAddress -> {
                    view.hideLoadingDialog();
                    AddressPresenter.this.propertyAddress = propertyAddress;
                }, throwable -> {
                    view.hideLoadingDialog();
                    view.showError(throwable);
                }));
    }

    @Override
    public void onDoneClicked() {
        String address = view.getAddress();
        if (address.isEmpty()) {
            view.showEmptyLocationError();
        } else {
            if (propertyAddress == null) {
                propertyAddress = new PropertyAddress();
                propertyAddress.setAddressLine1(address);
            }
            propertyAddress.setFullAddress(address);
            view.hideKeyboard();
            view.sendAddressToActivity(this.propertyAddress);
        }
    }

    @Override
    public void onClearClicked() {
        view.setAddress("");
    }

    @Override
    public void onAddressChanged(String string) {
        if (string.isEmpty()) {
            propertyAddress = null;
        }
    }
}
