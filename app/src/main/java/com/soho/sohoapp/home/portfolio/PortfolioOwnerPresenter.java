package com.soho.sohoapp.home.portfolio;

import com.soho.sohoapp.abs.AbsPresenter;

public class PortfolioOwnerPresenter implements AbsPresenter, PortfolioListContract.ViewActionsListener {
    private final PortfolioListContract.View view;

    public PortfolioOwnerPresenter(PortfolioListContract.View view) {
        this.view = view;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        System.out.println("PortfolioOwnerPresenter");
    }

    @Override
    public void stopPresenting() {

    }
}
