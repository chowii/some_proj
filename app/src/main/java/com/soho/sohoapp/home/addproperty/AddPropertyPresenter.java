package com.soho.sohoapp.home.addproperty;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.data.PropertyRole;

public class AddPropertyPresenter implements AbsPresenter, AddPropertyContract.ViewActionsListener {
    private final AddPropertyContract.View view;

    public AddPropertyPresenter(AddPropertyContract.View view) {
        this.view = view;
    }

    @Override
    public void startPresenting() {
        view.setActionsListener(this);
        view.showAddressFragment();
    }

    @Override
    public void stopPresenting() {
        // not needed yet
    }

    @Override
    public void onAddressSelected(PropertyAddress propertyAddress) {
        //todo: save property address
        view.showRelationFragment();
    }

    @Override
    public void onPropertyRoleSelected(PropertyRole propertyRole) {
        //todo: save property role
        //todo: show next params
    }
}
