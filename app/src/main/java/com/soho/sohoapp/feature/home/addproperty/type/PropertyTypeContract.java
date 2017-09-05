package com.soho.sohoapp.feature.home.addproperty.type;

import android.support.annotation.NonNull;

import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;

import java.util.List;

public interface PropertyTypeContract {

    interface ViewPresentable {
        void onPropertySelected(PropertyType propertyType);
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void setTypeList(@NonNull List<PropertyType> typeList);

        void showError(Throwable t);

        void sendTypeToActivity(PropertyType propertyType);

        void showLoadingIndicator();

        void hideLoadingIndicator();
    }
}
