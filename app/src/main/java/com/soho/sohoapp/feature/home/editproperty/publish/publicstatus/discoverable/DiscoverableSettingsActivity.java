package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.discoverable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.dialogs.LoadingDialog;
import com.soho.sohoapp.navigator.NavigatorImpl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class DiscoverableSettingsActivity extends AbsActivity implements DiscoverableSettingsContract.ViewInteractable {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.estimated_value_layout)
    LinearLayout estimatedValueLayout;

    @BindView(R.id.estimated_weekly_rent_layout)
    LinearLayout estimatedWeeklyRentLayout;

    @BindView(R.id.estimated_value_indicator)
    ImageView estimatedValueIndicator;

    @BindView(R.id.estimated_weekly_rent_indicator)
    ImageView estimatedWeeklyRentIndicator;

    @BindView(R.id.receive_offers_to_buy)
    SwitchCompat receiveOffersToBuy;

    @BindView(R.id.receive_offers_to_lease)
    SwitchCompat receiveOffersToLease;

    @BindView(R.id.estimated_value)
    EditText estimatedValue;

    @BindView(R.id.estimated_weekly_rent)
    EditText estimatedWeeklyRent;

    private DiscoverableSettingsContract.ViewPresentable presentable;
    private DiscoverableSettingsPresenter presenter;
    private LoadingDialog loadingDialog;

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull Property property) {
        Intent intent = new Intent(context, DiscoverableSettingsActivity.class);
        intent.putExtra(KEY_PROPERTY, property);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discoverable_settings);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(view -> presentable.onBackClicked());

        presenter = new DiscoverableSettingsPresenter(this, NavigatorImpl.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @Override
    public void showError(@NotNull Throwable throwable) {
        handleError(throwable);
    }

    @Override
    public void setPresentable(DiscoverableSettingsContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public void showEstimatedValue(boolean visible) {
        estimatedValueLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEstimatedValue(double value) {
        estimatedValue.setText(String.valueOf(value));
    }

    @Override
    public void showEstimatedWeeklyRent(boolean visible) {
        estimatedWeeklyRentLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEstimatedWeeklyRent(double value) {
        estimatedWeeklyRent.setText(String.valueOf(value));
    }

    @Override
    public void changeEstimatedValueIndicator(boolean isValid) {
        estimatedValueIndicator.setImageResource(isValid ? R.drawable.ic_green_exclaimation : R.drawable.ic_orange_exclaimation);
    }

    @Override
    public void changeEstimatedWeeklyRentIndicator(boolean isValid) {
        estimatedWeeklyRentIndicator.setImageResource(isValid ? R.drawable.ic_green_exclaimation : R.drawable.ic_orange_exclaimation);
    }

    @Override
    public void showToastMessage(@StringRes int resId) {
        super.showToast(resId);
    }

    @Override
    public void setReceiveOffersToBuyChecked(boolean checked) {
        receiveOffersToBuy.setChecked(checked);
    }

    @Override
    public void setReceiveOffersToLeaseChecked(boolean checked) {
        receiveOffersToLease.setChecked(checked);
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show(getString(R.string.common_loading));
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public boolean isReceiveOffersToBuyChecked() {
        return receiveOffersToBuy.isChecked();
    }

    @Override
    public boolean isReceiveOffersToLeaseChecked() {
        return receiveOffersToLease.isChecked();
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
    public String getEstimatedValue() {
        return estimatedValue.getText().toString();
    }

    @Override
    public String getEstimatedWeeklyRent() {
        return estimatedWeeklyRent.getText().toString();
    }

    @OnClick(R.id.save)
    void onSaveClicked() {
        presentable.onSaveClicked();
    }

    @OnCheckedChanged(R.id.receive_offers_to_buy)
    void onReceiveOfferToBuyChanged(boolean isChecked) {
        presentable.onReceiveOffersToBuyChanged(isChecked);
    }

    @OnCheckedChanged(R.id.receive_offers_to_lease)
    void onReceiveOfferToLeaseChanged(boolean isChecked) {
        presentable.onReceiveOffersToLeaseChanged(isChecked);
    }

    @OnTextChanged(R.id.estimated_value)
    void onEstimatedValueChanged(CharSequence text) {
        presentable.onEstimatedValueChanged(text.toString());
    }

    @OnTextChanged(R.id.estimated_weekly_rent)
    void onEstimatedWeeklyRentChanged(CharSequence text) {
        presentable.onEstimatedWeeklyRentChanged(text.toString());
    }

}
