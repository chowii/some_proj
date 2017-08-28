package com.soho.sohoapp.home.editproperty;

public interface EditPropertyContract {

    interface ViewActionsListener {
        void onBackClicked();

        void onAddPhotoClicked();
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);
    }
}
