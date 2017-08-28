package com.soho.sohoapp.home.editproperty;

import com.soho.sohoapp.abs.AbsPresenter;

public class EditPropertyPresenter implements AbsPresenter, EditPropertyContract.ViewActionsListener {
    private final EditPropertyContract.View view;

    public EditPropertyPresenter(EditPropertyContract.View view) {
        this.view = view;
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
    }

    @Override
    public void stopPresenting() {

    }
}
