package com.soho.sohoapp.feature.home.addproperty.address;

import com.soho.sohoapp.data.models.Location;

class AddressContract {

    public interface ViewPresentable {
        void onAddressClicked(String placeId, String fullAddress);

        void onDoneClicked();

        void onClearClicked();

        void onAddressChanged(String string);
    }

    public interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showLoadingDialog();

        void hideLoadingDialog();

        void showError(Throwable t);

        void setAddress(String s);

        void showEmptyLocationError();

        String getAddress();

        void sendAddressToActivity(Location address);

        void showKeyboard();

        void hideKeyboard();
    }
}
