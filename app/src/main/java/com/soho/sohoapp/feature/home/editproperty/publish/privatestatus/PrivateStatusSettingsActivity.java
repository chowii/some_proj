package com.soho.sohoapp.feature.home.editproperty.publish.privatestatus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.soho.sohoapp.Dependencies;
import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.dialogs.LoadingDialog;
import com.soho.sohoapp.feature.home.editproperty.data.Property;
import com.soho.sohoapp.navigator.NavigatorImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrivateStatusSettingsActivity extends AbsActivity implements PrivateStatusSettingsContract.ViewInteractable {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private PrivateStatusSettingsContract.ViewPresentable presentable;
    private PrivateStatusSettingsPresenter presenter;
    private LoadingDialog loadingDialog;

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull Property property) {
        Intent intent = new Intent(context, PrivateStatusSettingsActivity.class);
        intent.putExtra(KEY_PROPERTY, property);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_private_status);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(view -> presentable.onBackClicked());

        presenter = new PrivateStatusSettingsPresenter(this,
                NavigatorImpl.newInstance(this),
                Dependencies.DEPENDENCIES.getLogger());
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @OnClick(R.id.save)
    void onSaveClicked() {
        presentable.onSaveClicked();
    }

    @Override
    public void setPresentable(PrivateStatusSettingsContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public Property getPropertyFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        }
        return extras.getParcelable(KEY_PROPERTY);
    }

    @Override
    public void showRequestError() {
        showToast(R.string.common_loading_error);
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

}
