package com.soho.sohoapp.feature.home.editproperty.overview;

import android.databinding.DataBindingUtil;
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
import com.soho.sohoapp.databinding.FragmentEditOverviewBinding;
import com.soho.sohoapp.feature.home.editproperty.data.Property;
import com.soho.sohoapp.landing.BaseFragment;
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
        FragmentEditOverviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_overview, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        binding.setProperty(getProperty());

        marketplaceStateDesc = ButterKnife.findById(marketplaceState, R.id.title);
        verificationDesc = ButterKnife.findById(verification, R.id.title);
        verificationDesc.setText(R.string.edit_property_verification);

        presenter = new EditOverviewPresenter(this);
        presenter.startPresenting(savedInstanceState != null);
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.stopPresenting();
        super.onDestroyView();
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

    @OnClick(R.id.marketplaceState)
    void onMarketplaceStateClicked() {
        presentable.onMarketplaceStateClicked();
    }

    @OnClick(R.id.verification)
    void onVerificationClicked() {
        presentable.onVerificationClicked();
    }
}
