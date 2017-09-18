package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus;

public interface AutocompleteAddressContract {
    interface ViewPresentable {
        void onBackClicked();

        void onDoneClicked();
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);
    }
}
