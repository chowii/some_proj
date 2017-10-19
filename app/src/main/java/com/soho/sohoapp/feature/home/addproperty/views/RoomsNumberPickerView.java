package com.soho.sohoapp.feature.home.addproperty.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.soho.sohoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomsNumberPickerView extends LinearLayout {

    public static final int BEDROOMS_MIN_ANY = -1;
    public static final int BEDROOMS_STUDIO = 0;
    private static final int BEDROOMS_MIN = BEDROOMS_STUDIO;
    private static final int BEDROOMS_MAX = 99;
    private static final int BEDROOMS_DEFAULT_VALUE = 1;

    private static final int BATHROOMS_MIN_ANY = 0;
    private static final int BATHROOMS_MIN = 1;
    private static final int BATHROOMS_MAX = 99;
    private static final int BATHROOMS_DEFAULT_VALUE = 1;

    private static final int CARSPOT_MIN_ANY = 0;
    private static final int CARSPOTS_MIN = CARSPOT_MIN_ANY;
    private static final int CARSPOTS_MAX = 99;
    private static final int CARSPOTS_DEFAULT_VALUE = 0;

    private static final int STEP = 1;

    @BindView(R.id.bedrooms)
    NumberPickerView bedrooms;
    @BindView(R.id.bathrooms)
    NumberPickerView bathrooms;
    @BindView(R.id.carspots)
    NumberPickerView carspots;

    private double bedroomsCount = BEDROOMS_DEFAULT_VALUE;
    private double bathroomsCount = BATHROOMS_DEFAULT_VALUE;
    private double carspotsCount = CARSPOTS_DEFAULT_VALUE;

    @Nullable
    private OnRoomsNumberChangedListener pickerValueChangedListener;
    private boolean isAnyValues;
    private boolean showPlusSign = false;

    public RoomsNumberPickerView(Context context) {
        super(context);
        initView();
    }

    public RoomsNumberPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public double getBedroomsCount() {
        return bedroomsCount;
    }

    public double getBathroomsCount() {
        return bathroomsCount;
    }

    public double getCarspotsCount() {
        return carspotsCount;
    }

    public void setPickerValueChangedListener(@Nullable OnRoomsNumberChangedListener pickerValueChangedListener) {
        this.pickerValueChangedListener = pickerValueChangedListener;
    }

    public void setValues(double bedroomsCount, double bathroomsCount, double carspotsCount) {
        if (bedroomsCount >= BEDROOMS_MIN && bedroomsCount <= BEDROOMS_MAX) {
            this.bedroomsCount = bedroomsCount;
            bedrooms.setCurrentValue(bedroomsCount);
            showBedroomText(bedroomsCount);
        }
        if (bathroomsCount >= BATHROOMS_MIN && bedroomsCount <= BATHROOMS_MAX) {
            this.bathroomsCount = bathroomsCount;
            bathrooms.setCurrentValue(bathroomsCount);
            showBathroomText(bathroomsCount);
        }
        if (carspotsCount >= CARSPOTS_MIN && bedroomsCount <= CARSPOTS_MAX) {
            this.carspotsCount = carspotsCount;
            carspots.setCurrentValue(carspotsCount);
            showCarspotsText(carspotsCount);
        }
    }

    private void initView() {
        inflate(getContext(), R.layout.rooms_number_picker_view, this);
        ButterKnife.bind(this);
        setOrientation(VERTICAL);

        initBedroomsPicker();
        initBathroomsPicker();
        initCarspotsPicker();
    }

    private void initCarspotsPicker() {
        carspots.setMinValue(CARSPOTS_MIN);
        carspots.setMaxValue(CARSPOTS_MAX);
        carspots.setStep(STEP);
        carspots.setCurrentValue(CARSPOTS_DEFAULT_VALUE);
        showCarspotsText(CARSPOTS_DEFAULT_VALUE);
        carspots.setText(getResources()
                .getQuantityString(R.plurals.rooms_picker_carspots, CARSPOTS_DEFAULT_VALUE, CARSPOTS_DEFAULT_VALUE));
        carspots.setIcon(R.drawable.ic_feature_carspot);
        carspots.setListener(currentValue ->
        {
            carspotsCount = currentValue;
            showCarspotsText(currentValue);
            notifyListenerOfUpdates();
        });
    }

    private void showCarspotsText(double carspotsCount) {
        if (isAnyValues && carspotsCount == CARSPOT_MIN_ANY) {
            carspots.setText(getResources().getString(R.string.rooms_picker_any_carspot));
            return;
        }
        int stringRes = showPlusSign ? R.plurals.rooms_picker_carspots_with_plus : R.plurals.rooms_picker_carspots;
        carspots.setText(getResources().getQuantityString(stringRes, (int) carspotsCount, (int) carspotsCount));
    }

    private void initBathroomsPicker() {
        bathrooms.setMinValue(BATHROOMS_MIN);
        bathrooms.setMaxValue(BATHROOMS_MAX);
        bathrooms.setStep(STEP);
        bathrooms.setCurrentValue(BATHROOMS_DEFAULT_VALUE);
        showBathroomText(BATHROOMS_DEFAULT_VALUE);
        bathrooms.setIcon(R.drawable.ic_feature_bathroom);
        bathrooms.setListener(currentValue ->
        {
            bathroomsCount = currentValue;
            showBathroomText(currentValue);
            notifyListenerOfUpdates();
        });
    }

    private void showBathroomText(double bathsCount) {
        if (isAnyValues && bathsCount == BATHROOMS_MIN_ANY) {
            bathrooms.setText(getContext().getString(R.string.rooms_picker_any_bathroom));
            return;
        }
        int stringRes = showPlusSign ? R.plurals.rooms_picker_bathrooms_with_plus : R.plurals.rooms_picker_bathrooms;
        bathrooms.setText(getResources()
                .getQuantityString(stringRes, (int) bathsCount, (int) bathsCount));
    }

    private void initBedroomsPicker() {
        bedrooms.setMinValue(BEDROOMS_MIN);
        bedrooms.setMaxValue(BEDROOMS_MAX);
        bedrooms.setStep(STEP);
        bedrooms.setCurrentValue(BEDROOMS_DEFAULT_VALUE);
        showBedroomText(BEDROOMS_DEFAULT_VALUE);
        bedrooms.setIcon(R.drawable.ic_feature_bed);
        bedrooms.setListener(currentValue ->
        {
            bedroomsCount = currentValue;
            showBedroomText(currentValue);
            notifyListenerOfUpdates();
        });
    }

    private void showBedroomText(double roomsCount) {
        if (roomsCount == BEDROOMS_MIN_ANY && isAnyValues) {
            bedrooms.setText(getContext().getString(R.string.rooms_picker_any_bedroom));
            return;
        }
        switch ((int) roomsCount) {
            case BEDROOMS_STUDIO:
                bedrooms.setText(getContext().getString(R.string.rooms_picker_studio));
                break;
            default:
                int stringRes = showPlusSign ? R.plurals.rooms_picker_bedrooms_with_plus : R.plurals.rooms_picker_bedrooms;
                bedrooms.setText(getResources().getQuantityString(stringRes, (int) roomsCount, (int) roomsCount));
                break;
        }
    }

    private void notifyListenerOfUpdates() {
        if (pickerValueChangedListener != null)
            pickerValueChangedListener.pickerValuesUpdated(bedroomsCount, bathroomsCount, carspotsCount);
    }

    public void setAnyValues() {
        this.isAnyValues = true;

        bedrooms.setMinValue(BEDROOMS_MIN_ANY);
        bedrooms.setCurrentValue(BEDROOMS_MIN_ANY);
        showBedroomText(BEDROOMS_MIN_ANY);

        bathrooms.setMinValue(BATHROOMS_MIN_ANY);
        bathrooms.setCurrentValue(BATHROOMS_MIN_ANY);
        showBathroomText(BATHROOMS_MIN_ANY);

        carspots.setMinValue(CARSPOT_MIN_ANY);
        carspots.setCurrentValue(CARSPOT_MIN_ANY);
        showCarspotsText(CARSPOT_MIN_ANY);

    }

    public void setShowPlusSign(boolean showPlusSign) {
        this.showPlusSign = showPlusSign;
    }

    public interface OnRoomsNumberChangedListener {
        void pickerValuesUpdated(double bedrooms, double bathrooms, double carspots);
    }
}
