package com.soho.sohoapp.feature.home.editproperty.publish;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.feature.home.editproperty.data.Property;
import com.soho.sohoapp.feature.home.editproperty.data.PropertyStatus;
import com.soho.sohoapp.navigator.NavigatorInterface;

public class PropertyStatusPresenter implements AbsPresenter, PropertyStatusContract.ViewPresentable {
    private final PropertyStatusContract.ViewInteractable view;
    private final NavigatorInterface navigator;

    public PropertyStatusPresenter(PropertyStatusContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);

        Property property = view.getPropertyFromExtras();
        switch (property.getPropertyListing().getState()) {
            case PropertyStatus.PRIVATE:
                view.hidePublicIndicator();
                view.showPrivateIndicator();
                break;
            case PropertyStatus.PUBLIC:
                view.hidePrivateIndicator();
                view.showPublicIndicator();
                break;
        }
    }

    @Override
    public void stopPresenting() {
        //not needed here
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onPublicClicked() {
        //todo: go to public settings
    }

    @Override
    public void onPrivateClicked() {
        //todo: go to private settings
    }
}
