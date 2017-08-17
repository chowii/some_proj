package com.soho.sohoapp.home.addproperty;

import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.data.PropertyRole;

public interface AddPropertyContract {

    interface ViewActionsListener {
        void onAddressSelected(PropertyAddress propertyAddress);

        void onPropertyRoleSelected(PropertyRole propertyRole);
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        void showAddressFragment();

        void showRelationFragment();
    }
}
