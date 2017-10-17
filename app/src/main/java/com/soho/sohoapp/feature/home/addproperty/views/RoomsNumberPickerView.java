package com.soho.sohoapp.feature.home.addproperty.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.soho.sohoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomsNumberPickerView extends LinearLayout {
    private static final int BEDROOMS_MIN = 0;
    private static final int BEDROOMS_MAX = 99;
    private static final int BEDROOMS_DEFAULT_VALUE = 1;

    private static final int BATHROOMS_MIN = 1;
    private static final int BATHROOMS_MAX = 99;
    private static final int BATHROOMS_DEFAULT_VALUE = 1;

    private static final int CARSPOTS_MIN = 0;
    private static final int CARSPOTS_MAX = 99;
    private static final int CARSPOTS_DEFAULT_VALUE = 0;

    private static final int STEP = 1;

    @BindView(R.id.bedrooms)
    NumberPickerView bedrooms;
    @BindView(R.id.bathrooms)
    NumberPickerView bathrooms;
    @BindView(R.id.carspots)
    NumberPickerView carspots;

    private int bedroomsCount = BEDROOMS_DEFAULT_VALUE;
    private int bathroomsCount = BATHROOMS_DEFAULT_VALUE;
    private int carspotsCount = CARSPOTS_DEFAULT_VALUE;

    @Nullable
    private OnRoomsNumberChangedListener pickerValueChangedListener;

    public RoomsNumberPickerView(Context context) {
        super(context);
        initView();
    }

    public RoomsNumberPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public int getBedroomsCount() {
        return bedroomsCount;
    }

    public int getBathroomsCount() {
        return bathroomsCount;
    }

    public int getCarspotsCount() {
        return carspotsCount;
    }

    public void setPickerValueChangedListener(@Nullable OnRoomsNumberChangedListener pickerValueChangedListener) {
        this.pickerValueChangedListener = pickerValueChangedListener;
    }

    public void setValues(int bedroomsCount, int bathroomsCount, int carspotsCount) {
        if (bedroomsCount >= BEDROOMS_MIN && bedroomsCount <= BEDROOMS_MAX) {
            this.bedroomsCount = bedroomsCount;
            bedrooms.setCurrentValue(bedroomsCount);
            showBedroomText(bedroomsCount);
        }
        if (bathroomsCount >= BATHROOMS_MIN && bedroomsCount <= BATHROOMS_MAX) {
            this.bathroomsCount = bathroomsCount;
            bathrooms.setCurrentValue(bathroomsCount);
            bathrooms.setText(getResources()
                    .getQuantityString(R.plurals.rooms_picker_bathrooms, bathroomsCount, bathroomsCount));
        }
        if (carspotsCount >= CARSPOTS_MIN && bedroomsCount <= CARSPOTS_MAX) {
            this.carspotsCount = carspotsCount;
            carspots.setCurrentValue(carspotsCount);
            carspots.setText(getResources()
                    .getQuantityString(R.plurals.rooms_picker_carspots, carspotsCount, carspotsCount));
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
        carspots.setText(getResources()
                .getQuantityString(R.plurals.rooms_picker_carspots, CARSPOTS_DEFAULT_VALUE, CARSPOTS_DEFAULT_VALUE));
        carspots.setIcon(R.drawable.ic_feature_carspot);
        carspots.setListener(currentValue ->
        {
            carspotsCount = currentValue;
            notifyListenerOfUpdates();
            carspots.setText(getResources().getQuantityString(R.plurals.rooms_picker_carspots, currentValue, currentValue));
        });
    }

    private void initBathroomsPicker() {
        bathrooms.setMinValue(BATHROOMS_MIN);
        bathrooms.setMaxValue(BATHROOMS_MAX);
        bathrooms.setStep(STEP);
        bathrooms.setCurrentValue(BATHROOMS_DEFAULT_VALUE);
        bathrooms.setText(getResources()
                .getQuantityString(R.plurals.rooms_picker_bathrooms, BATHROOMS_DEFAULT_VALUE, BATHROOMS_DEFAULT_VALUE));
        bathrooms.setIcon(R.drawable.ic_feature_bathroom);
        bathrooms.setListener(currentValue ->
        {
            bathroomsCount = currentValue;
            notifyListenerOfUpdates();
            bathrooms.setText(getResources().getQuantityString(R.plurals.rooms_picker_bathrooms, currentValue, currentValue));
        });
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
            notifyListenerOfUpdates();
            showBedroomText(currentValue);
        });
    }

    private void showBedroomText(int rooms) {
        if (rooms == 0) {
            bedrooms.setText(getContext().getString(R.string.rooms_picker_studio));
        } else {
            bedrooms.setText(getResources().getQuantityString(R.plurals.rooms_picker_bedrooms, rooms, rooms));
        }
    }

    private void notifyListenerOfUpdates() {
        if (pickerValueChangedListener != null)
            pickerValueChangedListener.pickerValuesUpdated(bedroomsCount, bathroomsCount, carspotsCount);
    }

    public interface OnRoomsNumberChangedListener {
        void pickerValuesUpdated(int bedrooms, int bathrooms, int carspots);
    }
}
