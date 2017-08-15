package com.soho.sohoapp.home.addproperty;

public interface AddPropertyContract {

    interface ViewActionsListener {
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        void showAddressFragment();

        void showRelationFragment();
    }
}
