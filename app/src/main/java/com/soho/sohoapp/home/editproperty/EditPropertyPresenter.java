package com.soho.sohoapp.home.editproperty;

import android.net.Uri;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.home.editproperty.data.PropertyImage;
import com.soho.sohoapp.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.navigator.Navigator;

import java.util.ArrayList;
import java.util.List;

public class EditPropertyPresenter implements AbsPresenter, EditPropertyContract.ViewActionsListener {
    private final EditPropertyContract.View view;
    private final Navigator navigator;
    private final List<PropertyImage> propertyImages;

    public EditPropertyPresenter(EditPropertyContract.View view, Navigator navigator) {
        this.view = view;
        this.navigator = navigator;
        propertyImages = new ArrayList<>();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
        PortfolioProperty property = view.getProperty();

        initPropertyImages(property);
    }

    @Override
    public void stopPresenting() {
        //not needed yet
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onAddPhotoClicked() {
        view.showAddPhotoDialog();
    }

    @Override
    public void onTakeNewPhotoClicked() {
        view.capturePhoto();
    }

    @Override
    public void onChooseFromGalleryClicked() {
        view.pickImageFromGallery();
    }

    @Override
    public void onPhotoReady(String path) {
        clearImagesListIfNeeded();
        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setFilePath(path);
        propertyImages.add(propertyImage);
        setPropertyImages();
    }

    @Override
    public void onPhotoPicked(Uri uri) {
        clearImagesListIfNeeded();
        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setUri(uri);
        propertyImages.add(propertyImage);
        setPropertyImages();
    }

    private void clearImagesListIfNeeded() {
        if (propertyImages.size() == 1 && propertyImages.get(0).getDrawableId() != 0) {
            propertyImages.clear();
        }
    }

    private void setPropertyImages() {
        view.setPropertyImages(propertyImages);
        int size = propertyImages.size();
        if (size > 0) {
            view.setCurrentPropertyImage(size - 1);
        }
    }

    private void initPropertyImages(PortfolioProperty property) {
        List<PropertyImage> propertyImagesFromServer = property.getPropertyImageList();
        if (propertyImagesFromServer.isEmpty()) {
            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setDrawableId(R.drawable.others);
            propertyImages.add(propertyImage);
        } else {
            propertyImages.addAll(propertyImagesFromServer);
        }
        setPropertyImages();
    }
}

