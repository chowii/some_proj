package com.soho.sohoapp.home.editproperty;

public interface EditPropertyContract {

    interface ViewActionsListener {
        void onBackClicked();

        void onAddPhotoClicked();

        void onTakeNewPhotoClicked();

        void onChooseFromGalleryClicked();
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);

        void showAddPhotoDialog();

        void capturePhoto();
    }
}
