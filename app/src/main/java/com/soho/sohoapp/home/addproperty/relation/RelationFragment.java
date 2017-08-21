package com.soho.sohoapp.home.addproperty.relation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.addproperty.data.PropertyRole;
import com.soho.sohoapp.dialogs.LoadingDialog;
import com.soho.sohoapp.home.addproperty.dialogs.RelationsDialog;
import com.soho.sohoapp.landing.BaseFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RelationFragment extends BaseFragment implements RelationContract.View {
    public static final String TAG = RelationFragment.class.getSimpleName();

    private RelationPresenter presenter;
    private RelationContract.ViewActionsListener actionsListener;
    private LoadingDialog loadingDialog;

    @NonNull
    public static Fragment newInstance() {
        return new RelationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relation_to_property, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new RelationPresenter(this);
        presenter.startPresenting(savedInstanceState!=null);
    }

    @Override
    public void onDestroyView() {
        presenter.stopPresenting();
        presenter = null;
        super.onDestroyView();
    }

    @Override
    public void setActionsListener(RelationContract.ViewActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    @Override
    public void showRelationDialog(List<PropertyRole> relationList) {
        new RelationsDialog(getContext()).show(relationList, relation -> actionsListener.onOtherTypeSelected(relation));
    }

    @Override
    public void showError(Throwable t) {
        handleError(t);
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public void sendRoleToActivity(PropertyRole propertyRole) {
        Listener listener = (Listener) getActivity();
        listener.onPropertyRoleSelected(propertyRole);
    }

    @OnClick(R.id.owner)
    void onOwnerSelected() {
        actionsListener.onOwnerSelected();
    }

    @OnClick(R.id.agent)
    void onAgentSelected() {
        actionsListener.onAgentSelected();
    }

    @OnClick(R.id.other)
    void onOtherClicked() {
        actionsListener.onOtherClicked();
    }

    public interface Listener {
        void onPropertyRoleSelected(PropertyRole propertyRole);
    }
}
