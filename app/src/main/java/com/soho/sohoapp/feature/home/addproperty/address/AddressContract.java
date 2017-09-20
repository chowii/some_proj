package com.soho.sohoapp.feature.home.addproperty.address;

import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.feature.BaseViewInteractable;

class AddressContract {

    public interface ViewPresentable {
        void onAddressClicked(String placeId, String fullAddress);

        void onDoneClicked();

        void onClearClicked();

        void onAddressChanged(String string);
    }

    public interface ViewInteractable  extends BaseViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showLoadingDialog();

        void hideLoadingDialog();

        void setAddress(String s);

        void showEmptyLocationError();

        String getAddress();

        void sendAddressToActivity(Location address);

        void showKeyboard();

        void hideKeyboard();
    }
}
