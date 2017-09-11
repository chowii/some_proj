package com.soho.sohoapp.feature.home.editproperty;

import android.net.Uri;

import com.soho.sohoapp.feature.home.editproperty.data.Property;
import com.soho.sohoapp.feature.home.editproperty.data.PropertyImage;

import java.util.List;

interface EditPropertyContract {

    interface ViewPresentable {
        void onBackClicked();

        void onAddPhotoClicked();

        void onTakeNewPhotoClicked();

        void onChooseFromGalleryClicked();

        void onPhotoReady(String path);

        void onPhotoPicked(Uri uri);
    }

    interface ViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showAddPhotoDialog();

        void capturePhoto();

        void setCurrentPropertyImage(int position);

        void setPropertyImages(List<PropertyImage> propertyImages);

        void pickImageFromGallery();

        int getPropertyId();

        void showLoadingDialog();

        void hideLoadingDialog();

        void showLoadingError();

        void showAddress1(String address);

        void showAddress2(String address);

        void initTabs(Property property);
    }
}
