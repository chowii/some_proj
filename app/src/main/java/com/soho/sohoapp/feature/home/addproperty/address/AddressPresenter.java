package com.soho.sohoapp.feature.home.addproperty.address;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.location.LocationProviderInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddressPresenter implements AbsPresenter, AddressContract.ViewPresentable {
    private final AddressContract.ViewInteractable view;
    private final LocationProviderInterface locationProvider;
    private final CompositeDisposable compositeDisposable;
    private Location location;

    public AddressPresenter(AddressContract.ViewInteractable view, LocationProviderInterface locationProvider) {
        this.view = view;
        this.locationProvider = locationProvider;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        view.showKeyboard();
    }

    @Override
    public void stopPresenting() {
        view.hideKeyboard();
        compositeDisposable.clear();
    }

    @Override
    public void onAddressClicked(String placeId, String fullAddress) {
        view.showLoadingDialog();
        compositeDisposable.add(locationProvider.getLatLng(placeId)
                .flatMap(latLng -> locationProvider.getAddress(latLng, fullAddress))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(propertyAddress -> {
                    view.hideLoadingDialog();
                    AddressPresenter.this.location = propertyAddress;
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
            if (location == null) {
                location = new Location();
                location.setAddressLine1(address);
            }
            location.setFullAddress(address);
            view.hideKeyboard();
            view.sendAddressToActivity(this.location);
        }
    }

    @Override
    public void onClearClicked() {
        view.setAddress("");
    }

    @Override
    public void onAddressChanged(String newAddress) {
        if (newAddress.isEmpty()) {
            location = null;
        }
    }
}
