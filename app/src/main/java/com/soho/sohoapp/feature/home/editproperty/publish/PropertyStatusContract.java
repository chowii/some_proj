package com.soho.sohoapp.feature.home.editproperty.publish;

import com.soho.sohoapp.feature.home.editproperty.data.Property;
import com.soho.sohoapp.feature.home.editproperty.data.PropertyListing;

public interface PropertyStatusContract {

    interface ViewPresentable {
        void onBackClicked();

        void onPublicClicked();

        void onPrivateClicked();

        void onPropertyStatusUpdated(PropertyListing propertyListing);
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
