package com.soho.sohoapp.home.addproperty;

import com.soho.sohoapp.abs.AbsPresenter;

public class AddPropertyPresenter implements AbsPresenter, AddPropertyContract.ViewActionsListener {
    private final AddPropertyContract.View view;

    public AddPropertyPresenter(AddPropertyContract.View view) {
        this.view = view;
    }

    @Override
    public void startPresenting() {
        view.setActionsListener(this);
        view.showAddressFragment();
        view.showRelationFragment();
    }

    @Override
    public void stopPresenting() {
        // not needed yet
    }
}
