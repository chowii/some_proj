package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.rent;

import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.BaseViewInteractable;

public interface RentSettingsContract {

    interface ViewPresentable {
        void onBackClicked();

        void onSetTimeClicked();

        void onAppointmentClicked();

        void onDescriptionClicked();

        void onDescriptionUpdated(String description);
    }

    interface ViewInteractable extends BaseViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void enableInspectionTime(boolean enable);

        void showDescription(String description);

        Property getPropertyFromExtras();
    }
}
