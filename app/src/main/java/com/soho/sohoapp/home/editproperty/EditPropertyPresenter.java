package com.soho.sohoapp.home.editproperty;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.home.editproperty.data.PropertyImage;
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
        System.out.println("Choose from gallery");
    }

    @Override
    public void onPhotoReady(String path) {
        if (propertyImages.size() == 1 && propertyImages.get(0).getDrawableId() != 0) {
            propertyImages.clear();
        }

        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setFilePath(path);
        propertyImages.add(propertyImage);
        view.setPropertyImages(propertyImages);
    }
}
