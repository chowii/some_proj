package com.soho.sohoapp.feature.home.addproperty.type;

import android.support.annotation.NonNull;

import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;

import java.util.List;

public interface PropertyTypeContract {

    interface ViewActionsListener {
        void onPropertySelected(PropertyType propertyType);
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        void setTypeList(@NonNull List<PropertyType> typeList);

        void showError(Throwable t);

        void sendTypeToActivity(PropertyType propertyType);

        void showLoadingIndicator();

        void hideLoadingIndicator();
    }
}
