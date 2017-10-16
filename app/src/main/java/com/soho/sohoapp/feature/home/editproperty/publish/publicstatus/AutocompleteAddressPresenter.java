package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus;

import android.content.DialogInterface;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.location.LocationProviderInterface;
import com.soho.sohoapp.navigator.NavigatorInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AutocompleteAddressPresenter implements AbsPresenter, AutocompleteAddressContract.ViewPresentable {
    private final AutocompleteAddressContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private final LocationProviderInterface locationProvider;
    private final CompositeDisposable compositeDisposable;
    private Location location;

    public AutocompleteAddressPresenter(AutocompleteAddressContract.ViewInteractable view,
                                        NavigatorInterface navigator,
                                        LocationProviderInterface locationProvider) {
        this.view = view;
        this.navigator = navigator;
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
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onDoneClicked() {
        if (location == null) {
            view.showEmptyLocationError();
        } else {
            navigator.exitWithResultCodeOk(location);
        }
    }

    @Override
    public void onAddressChanged(String string) {
        if (string.isEmpty()) {
            location = null;
        }
    }

    @Override
    public void onClearClicked() {
        view.setAddress("");
    }

    @Override
    public void onAddressClicked(String placeId, String fullAddress) {
        if (view.confirmationDialogIsNeeded()) {
            view.showConfirmationDialog((dialogInterface, i) -> {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        selectAddress(placeId, fullAddress);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialogInterface.dismiss();
                        view.setAddress("");
                        break;
                }
            });
        } else {
            selectAddress(placeId, fullAddress);
        }
    }

    private void selectAddress(String placeId, String fullAddress) {
        view.showLoadingDialog();
        compositeDisposable.add(locationProvider.getLatLng(placeId)
                .flatMap(latLng -> locationProvider.getAddress(latLng, fullAddress))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newLocation -> {
                    view.hideLoadingDialog();
                    AutocompleteAddressPresenter.this.location = newLocation;
                }, throwable -> {
                    view.hideLoadingDialog();
                    view.showLoadingError();
                }));
    }
}
