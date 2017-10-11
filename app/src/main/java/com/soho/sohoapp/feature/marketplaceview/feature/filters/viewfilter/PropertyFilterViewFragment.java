package com.soho.sohoapp.feature.marketplaceview.feature.filters.viewfilter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.soho.sohoapp.Dependencies;
import com.soho.sohoapp.R;
import com.soho.sohoapp.customviews.TokenizedSuburbAutoCompleteTextView;
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs;
import com.soho.sohoapp.database.entities.Suburb;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.feature.home.addproperty.views.NumberPickerView;
import com.soho.sohoapp.feature.home.addproperty.views.RoomsNumberPickerView;
import com.soho.sohoapp.feature.marketplaceview.components.SuburbsAutocompleteAdapter;
import com.soho.sohoapp.landing.BaseFragment;
import com.tokenautocomplete.TokenCompleteTextView;
import com.wefika.horizontalpicker.HorizontalPicker;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.soho.sohoapp.extensions.IntExtKt.dpToPx;
import static com.soho.sohoapp.extensions.IntExtKt.toShortHand;
import static com.soho.sohoapp.extensions.StringExtKt.abbreviatedMoneyValueToInt;
import static com.soho.sohoapp.extensions.StringExtKt.withCurrency;

/**
 * Created by chowii on 27/08/17.
 */

public class PropertyFilterViewFragment extends BaseFragment implements PropertyFilterContract.ViewInteractable,
        TokenCompleteTextView.TokenListener<Suburb>,
        RoomsNumberPickerView.OnRoomsNumberChangedListener,
        RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener {

    private static final int RADIUS_MIN = 5;
    private static final int RADIUS_MAX = 1000;
    private static final int RADIUS_INCREMENT = 5;
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    @BindView(R.id.autocomplete_suburb)
    TokenizedSuburbAutoCompleteTextView suburbsAutocompleteTextView;
    @BindView(R.id.textview_price_range)
    TextView priceRangeTextView;
    @BindView(R.id.picker_from)
    HorizontalPicker priceFromPicker;
    @BindView(R.id.picker_to)
    HorizontalPicker priceToPicker;
    @BindView(R.id.rooms_selector)
    RoomsNumberPickerView roomsNumberPickerView;
    @BindView(R.id.property_status_radio_group)
    RadioGroup propertyStatusRadioGroup;
    @BindView(R.id.radio_button_only_active)
    RadioButton onlyActiveRadioButton;
    @BindView(R.id.number_picker_view_radius)
    NumberPickerView radiusPickerView;
    @BindView(R.id.ll_property_types)
    LinearLayout linearLayoutPropertyTypes;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.scrollview_form)
    ScrollView scrollView;

    CheckBox allCheckBox;
    List<CheckBox> propertyTypeCheckboxes;

    PropertyFilterPresenter presenter;

    // MARK: - ================== Lifecycle methods ==================

    public static PropertyFilterViewFragment newInstance(MarketplaceFilterWithSuburbs currentFilter) {
        PropertyFilterViewFragment fragment = new PropertyFilterViewFragment();
        fragment.presenter = new PropertyFilterPresenter(fragment, currentFilter);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        ButterKnife.bind(this, view);
        scrollView.setVisibility(View.GONE);
        presenter.startPresenting();
        setupSuburbAutocompleteAdapter();
        return view;
    }

    @Override
    public void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    // MARK: - ================== General methods ==================

    private void setupSuburbAutocompleteAdapter() {
        GoogleApiClient apiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .build();
        apiClient.connect();
        SuburbsAutocompleteAdapter adapter = new SuburbsAutocompleteAdapter(getContext(), apiClient, null);
        suburbsAutocompleteTextView.setAdapter(adapter);
        suburbsAutocompleteTextView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);
        suburbsAutocompleteTextView.setTokenListener(this);
    }

    private int indexOfPickerValue(String[] items, String selectedValue) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].equalsIgnoreCase(selectedValue)) {
                return i;
            }
        }
        return 0;
    }

    // MARK: - ================== PropertyFilterContract.ViewInteractable ==================

    @Override
    public void populateForm(MarketplaceFilterWithSuburbs filterWithSuburbs, List<PropertyType> propertyTypes) {
        suburbsAutocompleteTextView.clear();
        for (Suburb suburb : filterWithSuburbs.getSuburbs()) {
            suburbsAutocompleteTextView.addObject(suburb);
        }
        setupRadiusPicker(filterWithSuburbs);
        setupPricePickers(filterWithSuburbs);
        roomsNumberPickerView.setValues(((int) filterWithSuburbs.getMarketplaceFilter().getBedrooms()),
                ((int) filterWithSuburbs.getMarketplaceFilter().getBathrooms()),
                ((int) filterWithSuburbs.getMarketplaceFilter().getCarspots()));
        roomsNumberPickerView.setPickerValueChangedListener(this);
        propertyStatusRadioGroup.check(filterWithSuburbs.getMarketplaceFilter().getAllProperties() ? R.id.radio_button_all : R.id.radio_button_only_active);
        propertyStatusRadioGroup.setOnCheckedChangeListener(this);
        setupPropertyTypeSection(filterWithSuburbs, propertyTypes);
        scrollView.setVisibility(View.VISIBLE);
        copyChangesForSaleState(filterWithSuburbs.getMarketplaceFilter().isSaleFilter());
    }

    @Override
    public void finishApplyingFilters() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void toggleLoadingIndicator(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(@NotNull Throwable throwable) {
        handleError(throwable);
    }

    @Override
    public void showSaveSuccessful() {
        showSnackBar(getString(R.string.filters_saved_success_message), rootView);
    }

    // MARK: - ================== Setup related ==================

    private void setupPricePickers(MarketplaceFilterWithSuburbs filterWithSuburbs) {
        String[] pickerItems = getPickerItemsFor(filterWithSuburbs.getMarketplaceFilter().isSaleFilter() ? R.array.filter_buy_price_range : R.array.filter_rent_price_range);
        priceFromPicker.setValues(pickerItems);
        priceToPicker.setValues(pickerItems);

        priceToPicker.setSelectedItem(indexOfPickerValue(pickerItems, withCurrency(
                toShortHand(filterWithSuburbs.getMarketplaceFilter().getToPrice()))));

        priceFromPicker.setSelectedItem(indexOfPickerValue(pickerItems, withCurrency(
                toShortHand(filterWithSuburbs.getMarketplaceFilter().getFromPrice()))));

        priceToPicker.setOnItemSelectedListener(index ->
                presenter.getCurrentFilter().getMarketplaceFilter().setToPrice(
                        pickerItems[index].equalsIgnoreCase(getString(R.string.filter_type_all)) ? 0
                                : abbreviatedMoneyValueToInt(pickerItems[index])));

        priceFromPicker.setOnItemSelectedListener(index ->
                presenter.getCurrentFilter().getMarketplaceFilter().setFromPrice(
                        pickerItems[index].equalsIgnoreCase(getString(R.string.filter_type_all)) ? 0
                                : abbreviatedMoneyValueToInt(pickerItems[index])));
    }

    private void setupRadiusPicker(MarketplaceFilterWithSuburbs filterWithSuburbs) {
        radiusPickerView.setMinValue(RADIUS_MIN);
        radiusPickerView.setMaxValue(RADIUS_MAX);
        radiusPickerView.setStep(RADIUS_INCREMENT);
        radiusPickerView.setCurrentValue(filterWithSuburbs.getMarketplaceFilter().getRadius());
        radiusPickerView.setText(getResources()
                .getQuantityString(R.plurals.number_picker_kilometers, filterWithSuburbs.getMarketplaceFilter().getRadius(), filterWithSuburbs.getMarketplaceFilter().getRadius()));
        radiusPickerView.setListener(currentValue -> {
            presenter.getCurrentFilter().getMarketplaceFilter().setRadius(currentValue);
            radiusPickerView.setText(getResources().getQuantityString(R.plurals.number_picker_kilometers, currentValue, currentValue));
        });
    }

    private void setupPropertyTypeSection(MarketplaceFilterWithSuburbs filterWithSuburbs, List<PropertyType> propertyTypes) {
        linearLayoutPropertyTypes.removeAllViews();
        propertyTypeCheckboxes = new ArrayList<>();
        allCheckBox = checkBoxViewWith("All", "", filterWithSuburbs.getMarketplaceFilter().getPropertyTypes().size() == 0);
        propertyTypeCheckboxes.add(allCheckBox);
        linearLayoutPropertyTypes.addView(allCheckBox);
        for (PropertyType type : propertyTypes) {
            CheckBox checkBox = checkBoxViewWith(type.getLabel(), type.getKey(), filterWithSuburbs.getMarketplaceFilter().getPropertyTypes().contains(type.getKey()));
            propertyTypeCheckboxes.add(checkBox);
            linearLayoutPropertyTypes.addView(checkBox);
        }
    }

    private void copyChangesForSaleState(boolean isSale) {
        onlyActiveRadioButton.setText(isSale ? R.string.filter_property_status_active_sale : R.string.filter_property_status_active_rent);
        priceRangeTextView.setText(isSale ? R.string.filters_heading_price_range_sale : R.string.filters_heading_price_range_rent);
    }

    private CheckBox checkBoxViewWith(String title, String key, boolean checked) {
        CheckBox checkBox = ((CheckBox) getLayoutInflater().inflate(R.layout.view_property_type_checkbox, null));
        checkBox.setText(title);
        checkBox.setTag(key);
        checkBox.setOnCheckedChangeListener(this);
        checkBox.setChecked(checked);
        checkBox.setHeight(dpToPx(50, getResources()));
        return checkBox;
    }

    @NonNull
    private String[] getPickerItemsFor(@ArrayRes int resourceId) {
        return getResources().getStringArray(resourceId);
    }

    // MARK: - ================== TokenCompleteTextView.TokenListener ==================

    @Override
    public void onTokenAdded(Suburb token) {
        token.setMarketplaceFilterId(presenter.getCurrentFilter().getMarketplaceFilter().getId());
        presenter.getCurrentFilter().setSuburbs(suburbsAutocompleteTextView.getObjects());
    }

    @Override
    public void onTokenRemoved(Suburb token) {
        if (token.getId() != 0) {
            presenter.addSuburbToDelete(token);
        }
    }

    // MARK: - ================== OnRoomsNumberChangedListener ==================

    @Override
    public void pickerValuesUpdated(int bedrooms, int bathrooms, int carspots) {
        presenter.getCurrentFilter().getMarketplaceFilter().setBedrooms(bedrooms);
        presenter.getCurrentFilter().getMarketplaceFilter().setBathrooms(bathrooms);
        presenter.getCurrentFilter().getMarketplaceFilter().setCarspots(carspots);
    }

    // MARK: - ================== RadioGroup.OnCheckChangedListener ==================

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.radio_button_all:
                presenter.getCurrentFilter().getMarketplaceFilter().setAllProperties(true);
                break;
            case R.id.radio_button_only_active:
                presenter.getCurrentFilter().getMarketplaceFilter().setAllProperties(false);
                break;
            default:
                break;
        }
    }

    // MARK: - ================== CompoundButton.OnCheckChangedListener ==================

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Dependencies.DEPENDENCIES.getLogger().d(compoundButton.getTag().toString());
        if (compoundButton == allCheckBox && b) {
            for (CheckBox checkBox : propertyTypeCheckboxes) {
                checkBox.setChecked(false);
            }
            compoundButton.setChecked(true);
            presenter.getCurrentFilter().getMarketplaceFilter().getPropertyTypes().clear();
        } else {
            if (b) {
                presenter.getCurrentFilter().getMarketplaceFilter().addPropertyType(compoundButton.getTag().toString());
            } else {
                presenter.getCurrentFilter().getMarketplaceFilter().removePropertyType(compoundButton.getTag().toString());
            }
            if (allCheckBox != null)
                allCheckBox.setChecked(presenter.getCurrentFilter().getMarketplaceFilter().getPropertyTypes().size() == 0);
        }
    }

    // MARK: - ================== OnClicks ==================

    @OnClick(R.id.button_search)
    void onClickSearch(View view) {
        presenter.applyFilters();
    }

    @OnClick(R.id.button_save_search)
    void onClickSaveSearch(View view) {
        presenter.saveFilter();
    }
}
