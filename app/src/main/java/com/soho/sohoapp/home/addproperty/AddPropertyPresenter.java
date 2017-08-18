package com.soho.sohoapp.home.addproperty;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.data.PropertyRole;
import com.soho.sohoapp.data.PropertyType;

public class AddPropertyPresenter implements AbsPresenter, AddPropertyContract.ViewActionsListener {
    private final AddPropertyContract.View view;
    private PropertyRole propertyRole;

    public AddPropertyPresenter(AddPropertyContract.View view) {
        this.view = view;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
        if (!fromConfigChanges) {
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
        this.propertyRole = propertyRole;
        view.showPropertyTypeFragment();
    }

    @Override
    public void onPropertyTypeSelected(PropertyType propertyType) {
        //todo: save property type
        view.showInvestmentFragment(PropertyRole.OWNER.equals(propertyRole.getLabel()));
    }

    @Override
    public void onHomeOrInvestmentSelected(boolean isInvestment) {
        //todo: save result
        view.showRoomsFragment();
    }

    @Override
    public void onRoomsSelected(int bedrooms, int bathrooms, int carspots) {
        //todo: save result
        view.showMessage("Ready!");
    }
}
