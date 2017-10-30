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
import com.soho.sohoapp.dialogs.PortfolioTypesDialog;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.feature.home.addproperty.views.RoomsNumberPickerView;
import com.soho.sohoapp.landing.BaseFragment;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.utils.DrawableUtils;
import com.soho.sohoapp.utils.ViewUtils;
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

    @BindView(R.id.renovation)
    TextView txtRenovation;

    @BindView(R.id.portfolioType)
    TextView txtPortfolioType;

    @BindView(R.id.portfolio)
    LinearLayout portfolioLayout;

    @BindView(R.id.maskLayout)
    LinearLayout maskLayout;

    @BindView(R.id.maskDesc)
    TextView maskDesc;

    @BindView(R.id.portfolioDesc)
    TextView portfolioDesc;

    @BindView(R.id.propertyTypeLayout)
    LinearLayout propertyTypeLayout;

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
        getEditPropertyListener().onPropertyChangedEnabled();
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
                    break;
                case RequestCode.EDIT_PROPERTY_ADDRESS:
                    Location location = extras.getParcelable(NavigatorImpl.KEY_PROPERTY_LOCATION);
                    presentable.onPropertyAddressChanged(location);
                    break;
                case RequestCode.EDIT_PROPERTY_RENOVATION:
                    presentable.onRenovationChanged(extras.getString(NavigatorImpl.KEY_STRING));
                    break;
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.EDIT_PROPERTY_ARCHIVE_CONFIRMATION) {
            presentable.onArchiveConfirmed();
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
        getEditPropertyListener().onPropertyAddressChanged(location);
    }

    @Override
    public void notifyActivityAboutRoomsChanges(double bedrooms, double bathrooms, double carspots) {
        EditPropertyListener listener = (EditPropertyListener) getActivity();
        listener.onRoomsNumberChanged(bedrooms, bathrooms, carspots);
    }

    @Override
    public void notifyActivityAboutPropertyTypeChanged(String type) {
        getEditPropertyListener().onPropertyTypeChanged(type);
    }

    @Override
    public void notifyActivityAboutRenovationChanged(String renovation) {
        getEditPropertyListener().onRenovationChanged(renovation);
    }

    @Override
    public void notifyActivityAboutInvestmentStatusChanged(boolean isInvestment) {
        getEditPropertyListener().onInvestmentStatusChanged(isInvestment);
    }

    @Override
    public void notifyActivityAboutPropertyStatusChanged(String status) {
        getEditPropertyListener().onPropertyStatusChanged(status);
    }

    @Override
    public void notifyActivityAboutPropertyFinanceChanged(PropertyFinance finance) {
        getEditPropertyListener().onPropertyFinanceChanged(finance);
    }

    @Override
    public void showMaskAddress(boolean isMaskAddress) {
        maskAddress.setChecked(isMaskAddress);
    }

    @Override
    public void showRoomsNumber(double bedrooms, double bathrooms, double carspots) {
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
    public void showRenovation(String renovation) {
        txtRenovation.setText(renovation);
    }

    @Override
    public void showPortfolioTypesDialog() {
        //todo: replace deprecated dialog
        PortfolioTypesDialog portfolioTypesDialog = new PortfolioTypesDialog(getContext());
        portfolioTypesDialog.show(new PortfolioTypesDialog.Listener() {
            @Override
            public void onHomeClicked() {
                presentable.onHomeClicked();
            }

            @Override
            public void onInvestmentClicked() {
                presentable.onInvestmentClicked();
            }

            @Override
            public void onArchiveClicked() {
                presentable.onArchiveClicked();
            }
        });
    }

    @Override
    public void showPortfolioTypes(@StringRes int portfolioType) {
        txtPortfolioType.setText(portfolioType);
    }

    @Override
    public void disable() {
        investmentSummary.disable();
        propertyTypePicker.disable();
        roomsSelector.disable();

        ViewUtils.setDisabledBackground(getContext(), marketplaceState, verification, address,
                maskLayout, propertyTypeLayout, portfolioLayout, txtRenovation);

        ViewUtils.disableViews(marketplaceState, marketplaceStateDesc, verification,
                verificationDesc, address, maskAddress, maskDesc,
                portfolioLayout, portfolioDesc, txtRenovation);
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

    @OnClick(R.id.renovation)
    void onRenovationClicked() {
        presentable.onRenovationClicked();
    }

    @OnClick(R.id.portfolio)
    void onPortfolioClicked() {
        presentable.onPortfolioClicked();
    }

    private void initView() {
        marketplaceStateDesc = ButterKnife.findById(marketplaceState, R.id.title);
        verificationDesc = ButterKnife.findById(verification, R.id.title);
        verificationDesc.setText(R.string.edit_property_verification);
        investmentSummary.setListener(finance -> presentable.onPropertyFinanceChanged(finance));
        roomsSelector.setPickerValueChangedListener((bedrooms, bathrooms, carspots) ->
                presentable.onRoomsNumberChanged(bedrooms, bathrooms, carspots));
    }

    @Nullable
    private Bundle getExtras(Intent data) {
        return data != null ? data.getExtras() : null;
    }

    private EditPropertyListener getEditPropertyListener() {
        return (EditPropertyListener) getActivity();
    }

    public interface EditPropertyListener {
        void onPropertyAddressChanged(Location location);

        void onRoomsNumberChanged(double bedrooms, double bathrooms, double carspots);

        void onPropertyTypeChanged(String type);

        void onRenovationChanged(String renovation);

        void onInvestmentStatusChanged(boolean isInvestment);

        void onPropertyStatusChanged(String propertyStatus);

        void onPropertyFinanceChanged(PropertyFinance finance);

        void onPropertyChangedEnabled();
    }
}
