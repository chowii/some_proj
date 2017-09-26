package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.rent;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.logger.Logger;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.navigator.RequestCode;

public class RentSettingsPresenter implements AbsPresenter, RentSettingsContract.ViewPresentable {
    private final RentSettingsContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private final Logger logger;
    private Property property;

    public RentSettingsPresenter(RentSettingsContract.ViewInteractable view,
                                 NavigatorInterface navigator,
                                 Logger logger) {
        this.view = view;
        this.navigator = navigator;
        this.logger = logger;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        property = view.getPropertyFromExtras();
    }

    @Override
    public void stopPresenting() {
        //todo:
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onSetTimeClicked() {
        view.enableInspectionTime(true);
    }

    @Override
    public void onAppointmentClicked() {
        view.enableInspectionTime(false);
    }

    @Override
    public void onDescriptionClicked() {
        navigator.openPropertyDescriptionScreen(property.getDescription(), RequestCode.PROPERTY_RENT_SETTINGS_DESCRIPTION);
    }

    @Override
    public void onDescriptionUpdated(String description) {
        view.showDescription(description);
        property.setDescription(description);
    }
}
