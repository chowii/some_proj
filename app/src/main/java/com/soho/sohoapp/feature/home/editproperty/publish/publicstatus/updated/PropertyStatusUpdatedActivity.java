package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.updated;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.navigator.NavigatorImpl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.ButterKnife.findById;

public class PropertyStatusUpdatedActivity extends AbsActivity implements PropertyStatusUpdatedContract.ViewInteractable {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";
    private PropertyStatusUpdatedContract.ViewPresentable presentable;
    private PropertyStatusUpdatedPresenter presenter;

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull Property property) {
        Intent intent = new Intent(context, PropertyStatusUpdatedActivity.class);
        intent.putExtra(KEY_PROPERTY, property);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_status_updated);
        ButterKnife.bind(this);

        presenter = new PropertyStatusUpdatedPresenter(this, NavigatorImpl.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @Override
    public void setPresentable(PropertyStatusUpdatedContract.ViewPresentable presentable) {
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
    public void showStatus(@StringRes int propertyStatus) {
        TextView description = findById(this, R.id.status_desc);
        description.setText(propertyStatus);
    }

    @Override
    public void showAddress(String address) {
        TextView addressField = findById(this, R.id.address);
        addressField.setText(address);
    }

    @Override
    public void showNextVerificationButton() {
        Button next = findById(this, R.id.next);
        Button nextVerification = findById(this, R.id.next_verification);
        next.setVisibility(View.GONE);
        nextVerification.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(@NotNull Throwable throwable) {
        handleError(throwable);
    }

    @OnClick(R.id.next)
    void onNext() {
        presentable.onNextClicked();
    }

    @OnClick(R.id.next_verification)
    void onNextVerification() {
        showToast("TODO: go to verifications");
        presentable.onNextVerificationClicked();
    }
}
