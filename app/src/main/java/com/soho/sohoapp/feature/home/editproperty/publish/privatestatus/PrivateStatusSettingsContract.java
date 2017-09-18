package com.soho.sohoapp.feature.home.editproperty.publish.privatestatus;

import com.soho.sohoapp.data.models.Property;

public interface PrivateStatusSettingsContract {

    interface ViewPresentable {
        void onBackClicked();

        void onSaveClicked();
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        Property getPropertyFromExtras();

        void showRequestError();

        void showLoadingDialog();

        void hideLoadingDialog();
    }
}
