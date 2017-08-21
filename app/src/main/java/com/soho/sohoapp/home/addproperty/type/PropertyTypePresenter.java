package com.soho.sohoapp.home.addproperty.type;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.home.addproperty.data.PropertyType;
import com.soho.sohoapp.network.ApiClient;
import com.soho.sohoapp.utils.Converter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PropertyTypePresenter implements AbsPresenter, PropertyTypeContract.ViewActionsListener {
    private final PropertyTypeContract.View view;
    private final CompositeDisposable compositeDisposable;

    public PropertyTypePresenter(PropertyTypeContract.View view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
        view.showLoadingIndicator();
        Disposable disposable = ApiClient.getService().getPropertyTypes().map(Converter::toPropertyTypeList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(propertyTypes -> {
                    view.setTypeList(propertyTypes);
                    view.hideLoadingIndicator();
                }, throwable -> {
                    view.showError(throwable);
                    view.hideLoadingIndicator();
                });
        compositeDisposable.add(disposable);
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
