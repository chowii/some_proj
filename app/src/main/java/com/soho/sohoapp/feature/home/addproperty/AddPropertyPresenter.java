package com.soho.sohoapp.feature.home.addproperty;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.utils.Converter;
import com.soho.sohoapp.utils.QueryHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class AddPropertyPresenter implements AbsPresenter, AddPropertyContract.ViewPresentable {
    private final AddPropertyContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private final CompositeDisposable compositeDisposable;
    private PropertyRole propertyRole;
    private Location location;
    private PropertyType propertyType;
    private boolean isInvestment;
    private double bedrooms;
    private double bathrooms;
    private double carspots;

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
    public void onAddressSelected(Location location) {
        this.location = location;
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
    public void onRoomsSelected(double bedrooms, double bathrooms, double carspots) {
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.carspots = carspots;

        createProperty();
    }

    private void createProperty() {
        view.showLoadingDialog();
        QueryHashMap map = Converter.toMap(location, propertyRole, propertyType,
                isInvestment, bedrooms, bathrooms, carspots);

        compositeDisposable.add(DEPENDENCIES.getSohoService().createProperty(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sohoProperty -> {
                    view.hideLoadingDialog();
                    navigator.openEditPropertyScreen(sohoProperty.getId());
                    navigator.exitWithResultCodeOk();
                }, throwable -> {
                    view.showError(throwable);
                    view.hideLoadingDialog();
                }));
    }
}
