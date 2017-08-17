package com.soho.sohoapp.home.addproperty.relation;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.data.PropertyRole;
import com.soho.sohoapp.network.ApiClient;
import com.soho.sohoapp.utils.Converter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RelationPresenter implements AbsPresenter, RelationContract.ViewActionsListener {
    private final RelationContract.View view;
    private final CompositeDisposable compositeDisposable;

    public RelationPresenter(RelationContract.View view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
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
        Disposable disposable = ApiClient.getService()
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
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onOtherTypeSelected(PropertyRole propertyRole) {
        view.sendRoleToActivity(propertyRole);
    }
}
