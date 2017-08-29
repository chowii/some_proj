package com.soho.sohoapp.home.editproperty;

import com.soho.sohoapp.home.editproperty.data.PropertyImage;

import java.util.List;

public interface EditPropertyContract {

    interface ViewActionsListener {
        void onBackClicked();

        void onAddPhotoClicked();

        void onTakeNewPhotoClicked();

        void onChooseFromGalleryClicked();

        void onPhotoReady(String path);
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        void showAddPhotoDialog();

        void capturePhoto();

        void setPropertyImages(List<PropertyImage> propertyImages);
    }
}
