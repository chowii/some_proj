package com.soho.sohoapp.feature.home.addproperty;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.network.ApiClient;
import com.soho.sohoapp.utils.Converter;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddPropertyPresenter implements AbsPresenter, AddPropertyContract.ViewPresentable {
    private final AddPropertyContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private final CompositeDisposable compositeDisposable;
    private PropertyRole propertyRole;
    private PropertyAddress propertyAddress;
    private PropertyType propertyType;
    private boolean isInvestment;
    private int bedrooms;
    private int bathrooms;
    private int carspots;

    public AddPropertyPresenter(AddPropertyContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        if (!fromConfigChanges) {
            view.showAddressFragment();
        }
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    @Override
    public void onAddressSelected(PropertyAddress propertyAddress) {
        this.propertyAddress = propertyAddress;
        view.showRelationFragment();
    }

    @Override
    public void onPropertyRoleSelected(PropertyRole propertyRole) {
        this.propertyRole = propertyRole;
        view.showPropertyTypeFragment();
    }

    @Override
    public void onPropertyTypeSelected(PropertyType propertyType) {
        this.propertyType = propertyType;
        view.showInvestmentFragment(PropertyRole.OWNER.equals(propertyRole.getLabel()));
    }

    @Override
    public void onHomeOrInvestmentSelected(boolean isInvestment) {
        this.isInvestment = isInvestment;
        view.showRoomsFragment();
    }

    @Override
    public void onRoomsSelected(int bedrooms, int bathrooms, int carspots) {
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.carspots = carspots;

        createPromotion();
    }

    private void createPromotion() {
        view.showLoadingDialog();

        Map<String, Object> map = Converter.toMap(propertyAddress, propertyRole, propertyType,
                isInvestment, bedrooms, bathrooms, carspots);

        compositeDisposable.add(ApiClient.getService().createProperty(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sohoProperty -> {
                    view.hideLoadingDialog();
                    navigator.exitWithResultCodeOk();
                }, throwable -> {
                    view.showMessage("Error during creating new property");
                    view.hideLoadingDialog();
                    throwable.printStackTrace();
                }));
    }
}
