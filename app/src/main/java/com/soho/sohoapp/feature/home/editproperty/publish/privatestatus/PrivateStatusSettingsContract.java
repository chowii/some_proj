package com.soho.sohoapp.feature.home.editproperty.publish.privatestatus;

import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.BaseViewInteractable;

public interface PrivateStatusSettingsContract {

    interface ViewPresentable {
        void onBackClicked();

        void onSaveClicked();
    }

    interface ViewInteractable extends BaseViewInteractable{
        void setPresentable(ViewPresentable presentable);

        Property getPropertyFromExtras();


        void showLoadingDialog();

        void hideLoadingDialog();
    }
}
