package com.soho.sohoapp.feature.home.editproperty.publish.privatestatus;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.enums.PropertyStatus;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyListing;
import com.soho.sohoapp.logger.Logger;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.utils.Converter;
import com.soho.sohoapp.utils.QueryHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class PrivateStatusSettingsPresenter implements AbsPresenter, PrivateStatusSettingsContract.ViewPresentable {
    private final PrivateStatusSettingsContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private final Logger logger;
    private final CompositeDisposable compositeDisposable;

    public PrivateStatusSettingsPresenter(PrivateStatusSettingsContract.ViewInteractable view,
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
        view.showLoadingDialog();
        Property property = view.getPropertyFromExtras();
        PropertyListing propertyListing = property.getPropertyListing();
        propertyListing.setState(PropertyStatus.PRIVATE);
        QueryHashMap map = Converter.toMap(propertyListing);
        compositeDisposable.add(DEPENDENCIES.getSohoService().updatePropertyListing(property.getId(), map)
                .map(Converter::toPropertyListing)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newPropertyListing ->
                        {
                            view.hideLoadingDialog();
                            property.setPropertyListing(newPropertyListing);
                            navigator.exitWithResultCodeOk(property, true);
                        },
                        throwable -> {
                            view.hideLoadingDialog();
                            view.showError(throwable);
                            logger.e("Error during saving Property Listing", throwable);
                        }));
    }
}
