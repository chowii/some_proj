package com.soho.sohoapp.feature.home.editproperty;

import android.net.Uri;

import com.soho.sohoapp.data.models.Image;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.BaseViewInteractable;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;

import java.util.ArrayList;
import java.util.List;

interface EditPropertyContract {

    interface ViewPresentable {
        void onBackClicked();

        void onAddPhotoClicked();

        void onTakeNewPhotoClicked();

        void onChooseFromGalleryClicked();

        void onPhotoReady(String path);

        void onPhotoPicked(Uri uri);

        void onPropertyAddressChanged(Location location);

        void onSaveClicked();

        void onHeaderPhotoClicked(List<Image> images, int currentItem);

        void onRoomsNumberChanged(int bedrooms, int bathrooms, int carspots);

        void onPropertyTypeChanged(String type);
    }

    interface ViewInteractable extends BaseViewInteractable {
        void setPresentable(ViewPresentable presentable);

        void showAddPhotoDialog();

        void capturePhoto();

        void setCurrentPropertyImage(int position);

        void setPropertyImages(List<Image> propertyImages);

        void pickImageFromGallery();

        int getPropertyId();

        void showLoadingView();

        void hideLoadingView();

        void showAddress1(String address);

        void showAddress2(String address);

        void initTabs(Property property, ArrayList<PropertyType> propertyTypes);

        void showLoadingDialog();

        void hideLoadingDialog();
    }
}
