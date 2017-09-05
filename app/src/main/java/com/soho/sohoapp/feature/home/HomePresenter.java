package com.soho.sohoapp.feature.home;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.navigator.NavigatorInterface;

public class HomePresenter implements AbsPresenter, HomeContract.ViewPresentable {
    private final HomeContract.ViewInteractable view;
    private final NavigatorInterface navigator;

    public HomePresenter(HomeContract.ViewInteractable view, NavigatorInterface navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
        view.showMarketplaceTab();
    }

    @Override
    public void stopPresenting() {
        //not needed here
    }

    @Override
    public void onMarketplaceClicked() {
        view.showMarketplaceTab();
    }

    @Override
    public void onManageClicked() {
        view.showManageTab();
    }

    @Override
    public void onOffersClicked() {
        view.showOffersTab();
    }

    @Override
    public void onMoreClicked() {
        view.showMoreTab();
    }

    @Override
    public void onAddPropertyClicked() {
        navigator.openAddPropertyScreen();
    }
}
