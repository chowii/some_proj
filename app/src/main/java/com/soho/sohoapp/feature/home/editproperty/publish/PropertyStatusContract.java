package com.soho.sohoapp.feature.home.editproperty.publish;

import com.soho.sohoapp.feature.home.editproperty.data.Property;

public interface PropertyStatusContract {

    interface ViewPresentable {
        void onBackClicked();

        void onPublicClicked();

        void onPrivateClicked();
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showPublicIndicator();

        void hidePublicIndicator();

        void showPrivateIndicator();

        void hidePrivateIndicator();

        Property getPropertyFromExtras();
    }
}
