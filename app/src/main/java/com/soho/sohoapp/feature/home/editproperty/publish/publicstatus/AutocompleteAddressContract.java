package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus;

import android.content.DialogInterface;

public interface AutocompleteAddressContract {
    interface ViewPresentable {
        void onBackClicked();

        void onDoneClicked();

        void onAddressClicked(String placeId, String fullAddress);

        void onAddressChanged(String string);

        void onClearClicked();
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showLoadingDialog();

        void hideLoadingDialog();

        void setAddress(String s);

        void showLoadingError();

        void showKeyboard();

        void hideKeyboard();

        void showEmptyLocationError();

        boolean confirmationDialogIsNeeded();

        void showConfirmationDialog(DialogInterface.OnClickListener listener);
    }
}
