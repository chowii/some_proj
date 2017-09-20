package com.soho.sohoapp.feature.home.addproperty;

import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.feature.BaseViewInteractable;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;

interface AddPropertyContract {

    interface ViewPresentable {
        void onAddressSelected(Location location);

        void onPropertyRoleSelected(PropertyRole propertyRole);

        void onPropertyTypeSelected(PropertyType propertyType);

        void onHomeOrInvestmentSelected(boolean isInvestment);

        void onRoomsSelected(int bedrooms, int bathrooms, int carspots);
    }

    interface ViewInteractable extends BaseViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showAddressFragment();

        void showRelationFragment();

        void showPropertyTypeFragment();

        void showInvestmentFragment(boolean forOwner);

        void showRoomsFragment();

        void showLoadingDialog();

        void hideLoadingDialog();
    }
}
