package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.rent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.dialogs.LoadingDialog;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.navigator.RequestCode;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static butterknife.ButterKnife.findById;

public class RentSettingsActivity extends AbsActivity implements RentSettingsContract.ViewInteractable {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";
    private static final String TAG_DATE_PICKER_DIALOG = "TAG_DATE_PICKER_DIALOG";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rentPeriodOptions)
    RadioGroup rentPeriodOptions;

    @BindView(R.id.inspectionTimeOptions)
    RadioGroup inspectionTimeOptions;

    @BindView(R.id.inspectionTimeLayout)
    LinearLayout inspectionTimeLayout;

    @BindView(R.id.inspectionTimeDesc)
    TextView inspectionTimeDesc;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.availableFrom)
    TextView availableFrom;

    @BindView(R.id.rentTitle)
    EditText rentTitle;

    @BindView(R.id.priceValue)
    EditText priceValue;

    @BindView(R.id.priceIndicator)
    ImageView priceIndicator;

    private RentSettingsContract.ViewPresentable presentable;
    private RentSettingsPresenter presenter;
    private LoadingDialog loadingDialog;

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull Property property) {
        Intent intent = new Intent(context, RentSettingsActivity.class);
        intent.putExtra(KEY_PROPERTY, property);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_settings);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(view -> presentable.onBackClicked());
        initView();

        presenter = new RentSettingsPresenter(this, NavigatorImpl.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.PROPERTY_RENT_SETTINGS_DESCRIPTION) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                presentable.onDescriptionUpdated(extras.getString(NavigatorImpl.KEY_STRING));
            }
        }
    }

    @Override
    public void showError(@NotNull Throwable throwable) {
        handleError(throwable);
    }

    @Override
    public void setPresentable(RentSettingsContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public void enableInspectionTime(boolean enable) {
        inspectionTimeLayout.setEnabled(enable);
        if (enable) {
            inspectionTimeDesc.setTextColor(getResources().getColor(R.color.primaryText));
        } else {
            inspectionTimeDesc.setTextColor(getResources().getColor(R.color.hintText));
        }
    }

    @Override
    public void showDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void changePriceValidationIndicator(boolean priceIsValid) {
        if (priceIsValid) {
            priceIndicator.setImageResource(R.drawable.ic_green_exclaimation);
        } else {
            priceIndicator.setImageResource(R.drawable.ic_orange_exclaimation);
        }
    }

    @Override
    public void showDatePicker(Calendar calendar, DatePickerDialog.OnDateSetListener listener) {
        DatePickerDialog datePicker = DatePickerDialog.newInstance(listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.show(getFragmentManager(), TAG_DATE_PICKER_DIALOG);
    }

    @Override
    public void showAvailableFrom(String date) {
        availableFrom.setText(date);
    }

    @Override
    public void showToastMessage(@StringRes int resId) {
        super.showToast(resId);
    }

    @Override
    public void showPriceGuide(double value) {
        priceValue.setText(String.valueOf(value));
    }

    @Override
    public void showTitle(String title) {
        this.rentTitle.setText(title);
    }

    @Override
    public boolean isWeeklyPaymentChecked() {
        return R.id.leftOption == rentPeriodOptions.getCheckedRadioButtonId();
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
    public void selectMonthlyRentOption() {
        RadioButton monthlyRent = findById(rentPeriodOptions, R.id.rightOption);
        monthlyRent.setChecked(true);
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
    public String getRentTitle() {
        return rentTitle.getText().toString();
    }

    @Override
    public String getRentalPriceValue() {
        return priceValue.getText().toString();
    }

    @OnClick(R.id.description)
    void onDescriptionClicked() {
        presentable.onDescriptionClicked();
    }

    @OnClick(R.id.availability)
    void onAvailabilityClicked() {
        presentable.onAvailabilityClicked();
    }

    @OnClick(R.id.inspectionTimeLayout)
    void onInspectionTimeClicked() {
        presentable.onInspectionTimeClicked();
    }

    @OnClick(R.id.propertySizeLayout)
    void onPropertySizeClicked() {
        presentable.onPropertySizeClicked();
    }

    @OnClick(R.id.save)
    void onSaveClicked() {
        presentable.onSaveClicked();
    }

    @OnTextChanged(R.id.priceValue)
    void onPriceChanged(CharSequence text) {
        presentable.onPriceChanged(text.toString());
    }

    private void initView() {
        RadioButton weeklyOption = findById(rentPeriodOptions, R.id.leftOption);
        weeklyOption.setText(R.string.publish_property_rent_weekly);
        RadioButton monthlyOption = findById(rentPeriodOptions, R.id.rightOption);
        monthlyOption.setText(R.string.publish_property_rent_monthly);

        RadioButton setTimeOption = findById(inspectionTimeOptions, R.id.leftOption);
        setTimeOption.setText(R.string.publish_property_set_time);
        RadioButton appointmentOption = findById(inspectionTimeOptions, R.id.rightOption);
        appointmentOption.setText(R.string.publish_property_appointment_only);

        inspectionTimeOptions.setOnCheckedChangeListener((radioGroup, i) ->
        {
            switch (i) {
                case R.id.leftOption:
                    presentable.onSetTimeClicked();
                    break;
                case R.id.rightOption:
                    presentable.onAppointmentClicked();
                    break;
            }
        });
    }
}
