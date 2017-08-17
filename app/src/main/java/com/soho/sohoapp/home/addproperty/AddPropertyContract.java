package com.soho.sohoapp.home.addproperty;

import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.data.PropertyRole;
import com.soho.sohoapp.data.PropertyType;

public interface AddPropertyContract {

    interface ViewActionsListener {
        void onAddressSelected(PropertyAddress propertyAddress);

        void onPropertyRoleSelected(PropertyRole propertyRole);

        void onPropertyTypeSelected(PropertyType propertyType);
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        void showAddressFragment();

        void showRelationFragment();

        void showPropertyTypeFragment();
    }
}
