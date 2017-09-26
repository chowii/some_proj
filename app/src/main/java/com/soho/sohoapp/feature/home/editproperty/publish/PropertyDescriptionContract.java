package com.soho.sohoapp.feature.home.editproperty.publish;

import com.soho.sohoapp.feature.BaseViewInteractable;

public interface PropertyDescriptionContract {

    interface ViewPresentable {
        void onBackClicked();

        void onDoneClicked();
    }

    interface ViewInteractable extends BaseViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showDescription(String description);

        String getDescriptionFromExtras();

        String getDescription();

        void hideKeyboard();
    }
}
