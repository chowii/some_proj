package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.size;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.enums.AreaMeasurement;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.extensions.StringExtKt;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.navigator.NavigatorInterface;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;

public class PropertySizeActivity extends AbsActivity {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.measurement)
    RadioGroup rgMeasurement;

    @BindView(R.id.area)
    EditText txtArea;

    private NavigatorInterface navigator;
    private Property property;

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull Property property) {
        Intent intent = new Intent(context, PropertySizeActivity.class);
        intent.putExtra(KEY_PROPERTY, property);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_size);
        ButterKnife.bind(this);
        navigator = NavigatorImpl.newInstance(this);
        property = getPropertyFromExtras();

        initToolbar();
        initView();
    }

    private void initView() {
        RadioButton sqmOption = findById(rgMeasurement, R.id.leftOption);
        sqmOption.setText(R.string.property_size_measurement_sqm);
        RadioButton sqftOption = findById(rgMeasurement, R.id.rightOption);
        sqftOption.setText(R.string.property_size_measurement_sqft);

        if (property.getLandSize() > 0) {
            txtArea.setText(String.valueOf(property.getLandSize()));
        }

        if (AreaMeasurement.SQFT.equals(property.getLandSizeMeasurement())) {
            sqftOption.setChecked(true);
        } else {
            sqmOption.setChecked(true);
        }
    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.property_size_toolbar);
        toolbar.setNavigationOnClickListener(view -> navigator.exitCurrentScreen());
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_save) {
                validateAndSaveChanges();
            }
            return false;
        });
    }

    private void validateAndSaveChanges() {
        String area = txtArea.getText().toString().trim();
        if (area.isEmpty()) {
            showToast(R.string.property_size_area_not_valid);
        } else {
            if (R.id.leftOption == rgMeasurement.getCheckedRadioButtonId()) {
                property.setLandSizeMeasurement(AreaMeasurement.SQM);
            } else {
                property.setLandSizeMeasurement(AreaMeasurement.SQFT);
            }
            property.setLandSize(StringExtKt.toIntOrDefault(area, 0));
            navigator.exitWithResultCodeOk(property);
        }
    }

    private Property getPropertyFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        }
        return extras.getParcelable(KEY_PROPERTY);
    }
}
