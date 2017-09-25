package com.soho.sohoapp.feature.home.editproperty.publish;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.navigator.NavigatorInterface;

public class PropertyDescriptionPresenter implements AbsPresenter, PropertyDescriptionContract.ViewPresentable {
    private final PropertyDescriptionContract.ViewInteractable view;
    private final NavigatorInterface navigator;

    public PropertyDescriptionPresenter(PropertyDescriptionContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        view.showDescription(view.getDescriptionFromExtras());
    }

    @Override
    public void stopPresenting() {
        // not needed here
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onDoneClicked() {
        navigator.exitWithResultCodeOk(view.getDescription());
    }
}
