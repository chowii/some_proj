package com.soho.sohoapp.home.editproperty;

public interface EditPropertyContract {
    interface ViewActionsListener {
    }

    interface View {
        void setActionsListener(ViewActionsListener actionsListener);
    }
}
