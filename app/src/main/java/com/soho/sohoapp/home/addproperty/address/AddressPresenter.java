package com.soho.sohoapp.home.addproperty.address;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.location.LocationProvider;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class AddressPresenter implements AbsPresenter, AddressContract.ViewActionsListener {
    private final AddressContract.View view;
    private final LocationProvider locationProvider;
    private final CompositeSubscription compositeSubscription;
    private PropertyAddress propertyAddress;

    public AddressPresenter(AddressContract.View view, LocationProvider locationProvider) {
        this.view = view;
        this.locationProvider = locationProvider;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
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
        Subscription subscription = locationProvider.getLocationAddress(placeId, fullAddress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(propertyAddress -> {
                    view.hideLoadingDialog();
                    AddressPresenter.this.propertyAddress = propertyAddress;
                }, throwable -> {
                    view.hideLoadingDialog();
                    view.showError(throwable);
                });
        compositeSubscription.add(subscription);
    }

    @Override
    public void onDoneClicked() {
        String address = view.getAddress();
        if (address.isEmpty()) {
            view.showEmptyLocationError();
        } else {
            if (propertyAddress == null) {
                propertyAddress = new PropertyAddress();
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
