package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.updated;

import android.support.annotation.StringRes;

import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.BaseViewInteractable;

public interface PropertyStatusUpdatedContract {

    interface ViewPresentable {
        void onNextClicked();

        void onNextVerificationClicked();
    }

    interface ViewInteractable extends BaseViewInteractable {
        void setPresentable(ViewPresentable presentable);

        Property getPropertyFromExtras();

        void showStatus(@StringRes int propertyStatus);

        void showAddress(String address);

        void showNextVerificationButton();
    }
}
