package com.soho.sohoapp.feature.home.addproperty.type;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.network.ApiClient;
import com.soho.sohoapp.utils.Converter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PropertyTypePresenter implements AbsPresenter, PropertyTypeContract.ViewPresentable {
    private final PropertyTypeContract.ViewInteractable view;
    private final CompositeDisposable compositeDisposable;

    public PropertyTypePresenter(PropertyTypeContract.ViewInteractable view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        view.showLoadingIndicator();
        compositeDisposable.add(ApiClient.getService().getPropertyTypes()
                .map(Converter::toPropertyTypeList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(propertyTypes -> {
                    view.setTypeList(propertyTypes);
                    view.hideLoadingIndicator();
                }, throwable -> {
                    view.showError(throwable);
                    view.hideLoadingIndicator();
                }));
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    @Override
    public void onPropertySelected(PropertyType propertyType) {
        view.sendTypeToActivity(propertyType);
    }
}
