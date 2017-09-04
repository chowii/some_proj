package com.soho.sohoapp.feature.home.addproperty.address;

import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;

public class AddressContract {

    public interface ViewActionsListener {
        void onAddressClicked(String placeId, String fullAddress);

        void onDoneClicked();

        void onClearClicked();

        void onAddressChanged(String string);
    }

    public interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        void showLoadingDialog();

        void hideLoadingDialog();

        void showError(Throwable t);

        void setAddress(String s);

        void showEmptyLocationError();

        String getAddress();

        void sendAddressToActivity(PropertyAddress address);

        void showKeyboard();

        void hideKeyboard();
    }
}