package com.soho.sohoapp.home.portfolio;

import com.soho.sohoapp.abs.AbsPresenter;

public class PortfolioManagerPresenter implements AbsPresenter, PortfolioListContract.ViewActionsListener {
    private final PortfolioListContract.View view;

    public PortfolioManagerPresenter(PortfolioListContract.View view) {
        this.view = view;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        System.out.println("PortfolioManagerPresenter");
    }

    @Override
    public void stopPresenting() {

    }
}
