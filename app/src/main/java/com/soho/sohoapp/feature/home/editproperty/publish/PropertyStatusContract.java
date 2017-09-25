package com.soho.sohoapp.feature.home.editproperty.publish;

import com.soho.sohoapp.data.models.Property;

public interface PropertyStatusContract {

    interface ViewPresentable {
        void onBackClicked();

        void onPublicClicked();

        void onPrivateClicked();

        void onPropertyStatusUpdated(Property property);
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
