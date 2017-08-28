package com.soho.sohoapp.home.editproperty;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.navigator.Navigator;

public class EditPropertyPresenter implements AbsPresenter, EditPropertyContract.ViewActionsListener {
    private final EditPropertyContract.View view;
    private final Navigator navigator;

    public EditPropertyPresenter(EditPropertyContract.View view, Navigator navigator) {
        this.view = view;
        this.navigator = navigator;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
    }

    @Override
    public void stopPresenting() {

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
}
