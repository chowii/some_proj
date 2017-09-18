package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.navigator.NavigatorInterface;

public class AutocompleteAddressPresenter implements AbsPresenter, AutocompleteAddressContract.ViewPresentable {
    private AutocompleteAddressContract.ViewInteractable view;
    private NavigatorInterface navigator;

    public AutocompleteAddressPresenter(AutocompleteAddressContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
    }

    @Override
    public void stopPresenting() {
        // todo:
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onDoneClicked() {
        // todo: save result
    }
}
