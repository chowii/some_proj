package com.soho.sohoapp.feature.home.editproperty.publish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.navigator.RequestCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.ButterKnife.findById;

public class PropertyStatusActivity extends AbsActivity implements PropertyStatusContract.ViewInteractable {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.publicStatus)
    LinearLayout publicStatus;

    @BindView(R.id.privateStatus)
    LinearLayout privateStatus;

    private PropertyStatusContract.ViewPresentable presentable;
    private PropertyStatusPresenter presenter;

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull Property property) {
        Intent intent = new Intent(context, PropertyStatusActivity.class);
        intent.putExtra(KEY_PROPERTY, property);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_status);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(view -> presentable.onBackClicked());
        initView();

        presenter = new PropertyStatusPresenter(this, NavigatorImpl.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.PROPERTY_STATUS_UPDATE) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                presentable.onPropertyStatusUpdated(extras.getParcelable(NavigatorImpl.KEY_PROPERTY),
                        extras.getBoolean(NavigatorImpl.KEY_VERIFICATION_COMPLETED));
            }
        }
    }

    @Override
    public void setPresentable(PropertyStatusContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public void showPublicIndicator() {
        showIndicator(publicStatus);
    }

    @Override
    public void hidePublicIndicator() {
        hideIndicator(publicStatus);
    }

    @Override
    public void showPrivateIndicator() {
        showIndicator(privateStatus);
    }

    @Override
    public void hidePrivateIndicator() {
        hideIndicator(privateStatus);
    }

    @Override
    public Property getPropertyFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        }
        return extras.getParcelable(KEY_PROPERTY);
    }

    @OnClick(R.id.publicStatus)
    void onPublicClicked() {
        presentable.onPublicClicked();
    }

    @OnClick(R.id.privateStatus)
    void onPrivateClicked() {
        presentable.onPrivateClicked();
    }

    private void showIndicator(LinearLayout layout) {
        findById(layout, R.id.arrow).setVisibility(View.GONE);
        findById(layout, R.id.indicator).setVisibility(View.VISIBLE);
        findById(layout, R.id.update).setVisibility(View.VISIBLE);
    }

    private void hideIndicator(LinearLayout layout) {
        findById(layout, R.id.indicator).setVisibility(View.INVISIBLE);
        findById(layout, R.id.update).setVisibility(View.GONE);
        findById(layout, R.id.arrow).setVisibility(View.VISIBLE);
    }

    private void initView() {
        TextView publicTitle = findById(publicStatus, R.id.title);
        publicTitle.setText(R.string.publish_property_public);
        TextView publicDesc = findById(publicStatus, R.id.description);
        publicDesc.setText(R.string.publish_property_public_desc);

        TextView privateTitle = findById(privateStatus, R.id.title);
        privateTitle.setText(R.string.publish_property_private);
        TextView privateDesc = findById(privateStatus, R.id.description);
        privateDesc.setText(R.string.publish_property_private_desc);

    }
}
