package com.soho.sohoapp.feature.home.addproperty.investment;

import com.soho.sohoapp.abs.AbsPresenter;

public class InvestmentPresenter implements AbsPresenter, InvestmentContract.ViewActionsListener {
    private final InvestmentContract.View view;

    public InvestmentPresenter(InvestmentContract.View view) {
        this.view = view;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
        if (view.isForOwner()) {
            view.showOwnerQuestion();
            view.showOwnerHomeButton();
            view.showOwnerInvestmentButton();
        } else {
            view.showAgentQuestion();
            view.showAgentHomeButton();
            view.showAgentInvestmentButton();
        }
    }

    @Override
    public void stopPresenting() {
        //not needed here
    }

    @Override
    public void onHomeClicked() {
        view.sendResultToActivity(false);
    }

    @Override
    public void onInvestmentClicked() {
        view.sendResultToActivity(true);
    }
}
