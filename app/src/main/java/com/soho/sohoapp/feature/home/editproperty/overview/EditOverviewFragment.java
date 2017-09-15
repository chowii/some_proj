package com.soho.sohoapp.feature.home.editproperty.overview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.editproperty.data.Property;
import com.soho.sohoapp.feature.home.portfolio.data.PropertyFinance;
import com.soho.sohoapp.landing.BaseFragment;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.utils.DrawableUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditOverviewFragment extends BaseFragment implements EditOverviewContract.ViewInteractable {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";

    @BindView(R.id.marketplaceState)
    LinearLayout marketplaceState;

    @BindView(R.id.verification)
    LinearLayout verification;

    @BindView(R.id.investmentSummary)
    InvestmentSummaryView investmentSummary;

    private EditOverviewContract.ViewPresentable presentable;
    private EditOverviewPresenter presenter;
    private TextView marketplaceStateDesc;
    private TextView verificationDesc;

    @NonNull
    public static Fragment newInstance(@NonNull Property property) {
        Fragment fragment = new EditOverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PROPERTY, property);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_overview, container, false);
//        FragmentEditOverviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_overview, container, false);
//        View view = binding.getRoot();
//        binding.setProperty(getProperty());
        ButterKnife.bind(this, view);

        initView();

        presenter = new EditOverviewPresenter(this, NavigatorImpl.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.stopPresenting();
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.PROPERTY_STATUS_UPDATE) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                presentable.onPropertyStatusUpdated(extras.getParcelable(NavigatorImpl.KEY_PROPERTY_LISTING));
            }
        }
    }

    @Override
    public void setPresentable(EditOverviewContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public Property getProperty() {
        return getArguments().getParcelable(KEY_PROPERTY);
    }

    @Override
    public void showMarketplaceState(@StringRes int state) {
        marketplaceStateDesc.setText(state);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMarketplaceStateIndicator(@ColorRes int color) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_circle_state).mutate();
        DrawableUtils.setBackgroundColor(ContextCompat.getColor(getContext(), color), drawable);
        marketplaceStateDesc.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        verificationDesc.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    @Override
    public void showVerificationSection() {
        verification.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideVerificationSection() {
        verification.setVisibility(View.GONE);
    }

    @Override
    public void setPropertyFinance(PropertyFinance finance) {
        investmentSummary.setPropertyFinance(finance);
    }

    @OnClick(R.id.marketplaceState)
    void onMarketplaceStateClicked() {
        presentable.onMarketplaceStateClicked();
    }

    @OnClick(R.id.verification)
    void onVerificationClicked() {
        presentable.onVerificationClicked();
    }

    private void initView() {
        marketplaceStateDesc = ButterKnife.findById(marketplaceState, R.id.title);
        verificationDesc = ButterKnife.findById(verification, R.id.title);
        verificationDesc.setText(R.string.edit_property_verification);
    }
}
