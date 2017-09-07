package com.soho.sohoapp.feature.home.addproperty.relation;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole;
import com.soho.sohoapp.utils.Converter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;


public class RelationPresenter implements AbsPresenter, RelationContract.ViewPresentable {
    private final RelationContract.ViewInteractable view;
    private final CompositeDisposable compositeDisposable;

    RelationPresenter(RelationContract.ViewInteractable view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    @Override
    public void onOwnerSelected() {
        view.sendRoleToActivity(PropertyRole.createOwnerRole());
    }

    @Override
    public void onAgentSelected() {
        view.sendRoleToActivity(PropertyRole.createAgentRole());
    }

    @Override
    public void onOtherClicked() {
        view.showLoadingDialog();
        compositeDisposable.add(DEPENDENCIES.getSohoService()
                .getPropertyUserRoles()
                .map(Converter::toPropertyRoleList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(propertyRoles -> {
                    view.hideLoadingDialog();
                    view.showRelationDialog(propertyRoles);
                }, throwable -> {
                    view.hideLoadingDialog();
                    view.showError(throwable);
                }));

    }

    @Override
    public void onOtherTypeSelected(PropertyRole propertyRole) {
        view.sendRoleToActivity(propertyRole);
    }
}
