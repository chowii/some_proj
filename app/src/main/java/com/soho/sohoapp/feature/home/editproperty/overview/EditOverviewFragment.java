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
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.PickerItem;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyFinance;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.feature.home.addproperty.views.RoomsNumberPickerView;
import com.soho.sohoapp.feature.home.editproperty.verification.VerificationActivity;
import com.soho.sohoapp.landing.BaseFragment;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.utils.DrawableUtils;
import com.soho.sohoapp.views.TypePicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class EditOverviewFragment extends BaseFragment implements EditOverviewContract.ViewInteractable {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";
    private static final String KEY_PROPERTY_TYPES = "KEY_PROPERTY_TYPES";

    @BindView(R.id.marketplaceState)
    LinearLayout marketplaceState;

    @BindView(R.id.verification)
    LinearLayout verification;

    @BindView(R.id.investmentSummary)
    InvestmentSummaryView investmentSummary;

    @BindView(R.id.maskAddress)
    SwitchCompat maskAddress;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.propertyTypePicker)
    TypePicker propertyTypePicker;

    @BindView(R.id.roomsSelector)
    RoomsNumberPickerView roomsSelector;

    private EditOverviewContract.ViewPresentable presentable;
    private EditOverviewPresenter presenter;
    private TextView marketplaceStateDesc;
    private TextView verificationDesc;

    @NonNull
    public static Fragment newInstance(@NonNull Property property, @NonNull ArrayList<PropertyType> propertyTypes) {
        Fragment fragment = new EditOverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PROPERTY, property);
        bundle.putParcelableArrayList(KEY_PROPERTY_TYPES, propertyTypes);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_overview, container, false);
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
        Bundle extras = getExtras(data);
        if (resultCode == Activity.RESULT_OK && extras != null) {
            switch (requestCode) {
                case RequestCode.EDIT_PROPERTY_STATUS_UPDATE:
                    presentable.onPropertyStatusUpdated(extras.getParcelable(NavigatorImpl.KEY_PROPERTY),
                            extras.getBoolean(NavigatorImpl.KEY_VERIFICATION_COMPLETED));
                    break;
                case RequestCode.PROPERTY_VERIFICATIONS_REQUEST_CODE:
                    if (extras.containsKey(NavigatorImpl.KEY_VERIFICATIONS_UPDATED)) {
                        List verificationsList = new ArrayList();
                        Collections.addAll(verificationsList, extras.getParcelableArray(NavigatorImpl.KEY_VERIFICATIONS_UPDATED));
                        presentable.onPropertyVerificationsUpdated(verificationsList);
                    }
                case RequestCode.EDIT_PROPERTY_ADDRESS:
                    Location location = extras.getParcelable(NavigatorImpl.KEY_PROPERTY_LOCATION);
                    presentable.onPropertyAddressChanged(location);
                    notifyActivityAboutAddressChanges(location);
                    break;
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

    @Override
    public void showPropertyAddress(String address) {
        this.address.setText(address);
    }

    @Override
    public void notifyActivityAboutAddressChanges(Location location) {
        EditPropertyListener listener = (EditPropertyListener) getActivity();
        listener.onPropertyAddressChanged(location);
    }

    @Override
    public void notifyActivityAboutRoomsChanges(int bedrooms, int bathrooms, int carspots) {
        EditPropertyListener listener = (EditPropertyListener) getActivity();
        listener.onRoomsNumberChanged(bedrooms, bathrooms, carspots);
    }

    @Override
    public void showMaskAddress(boolean isMaskAddress) {
        maskAddress.setChecked(isMaskAddress);
    }

    @Override
    public void showRoomsNumber(int bedrooms, int bathrooms, int carspots) {
        roomsSelector.setValues(bedrooms, bathrooms, carspots);
    }

    @Override
    public List<PropertyType> getPropertyTypesFromExtras() {
        return getArguments().getParcelableArrayList(KEY_PROPERTY_TYPES);
    }

    @Override
    public void initPropertyTypes(List<PickerItem> pickerItems, int currentType) {
        propertyTypePicker.setPropertyTypes(pickerItems, currentType);
        propertyTypePicker.setListener(pickerItem -> presentable.onPropertyTypeChanged(pickerItem));
    }

    @Override
    public void notifyActivityAboutPropertyTypeChanged(String type) {
        EditPropertyListener listener = (EditPropertyListener) getActivity();
        listener.onPropertyTypeChanged(type);
    }

    @OnClick(R.id.marketplaceState)
    void onMarketplaceStateClicked() {
        presentable.onMarketplaceStateClicked();
    }

    @OnClick(R.id.verification)
    void onVerificationClicked() {
        presentable.onVerificationClicked();
    }

    @OnClick(R.id.address)
    void onAddressClicked() {
        presentable.onAddressClicked();
    }

    @OnCheckedChanged(R.id.maskAddress)
    void onMaskAddressChanged(boolean isChecked) {
        presentable.onMaskAddressChanged(isChecked);
    }

    private void initView() {
        marketplaceStateDesc = ButterKnife.findById(marketplaceState, R.id.title);
        verificationDesc = ButterKnife.findById(verification, R.id.title);
        verificationDesc.setText(R.string.edit_property_verification);
        roomsSelector.setPickerValueChangedListener((bedrooms, bathrooms, carspots) -> {
            presentable.onRoomsNumberChanged(bedrooms, bathrooms, carspots);
        });
    }

    @Nullable
    private Bundle getExtras(Intent data) {
        return data != null ? data.getExtras() : null;
    }

    public interface EditPropertyListener {
        void onPropertyAddressChanged(Location location);

        void onRoomsNumberChanged(int bedrooms, int bathrooms, int carspots);

        void onPropertyTypeChanged(String type);
    }
}
