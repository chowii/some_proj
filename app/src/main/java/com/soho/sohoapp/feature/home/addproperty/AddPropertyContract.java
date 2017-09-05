package com.soho.sohoapp.feature.home.addproperty;

import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;

interface AddPropertyContract {

    interface ViewActionsListener {
        void onAddressSelected(PropertyAddress propertyAddress);

        void onPropertyRoleSelected(PropertyRole propertyRole);

        void onPropertyTypeSelected(PropertyType propertyType);

        void onHomeOrInvestmentSelected(boolean isInvestment);

        void onRoomsSelected(int bedrooms, int bathrooms, int carspots);
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        void showAddressFragment();

        void showRelationFragment();

        void showPropertyTypeFragment();

        void showInvestmentFragment(boolean forOwner);

        void showRoomsFragment();

        void showMessage(String s);

        void showLoadingDialog();

        void hideLoadingDialog();
    }
}
