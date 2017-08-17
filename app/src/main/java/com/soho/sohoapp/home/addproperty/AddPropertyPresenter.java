package com.soho.sohoapp.home.addproperty;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.data.PropertyRole;
import com.soho.sohoapp.data.PropertyType;

public class AddPropertyPresenter implements AbsPresenter, AddPropertyContract.ViewActionsListener {
    private final AddPropertyContract.View view;

    public AddPropertyPresenter(AddPropertyContract.View view) {
        this.view = view;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
        if(!fromConfigChanges) {
            view.showAddressFragment();
        }
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
        view.showPropertyTypeFragment();
    }

    @Override
    public void onPropertyTypeSelected(PropertyType propertyType) {
        //todo: save property type
        System.out.println("Property Type: " + propertyType.getLabel());
    }
}
