package com.soho.sohoapp.feature.home.editproperty.publish;

import com.soho.sohoapp.feature.home.editproperty.data.Property;

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
