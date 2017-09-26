package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.rent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.navigator.RequestCode;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.ButterKnife.findById;
import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class RentSettingsActivity extends AbsActivity implements RentSettingsContract.ViewInteractable {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";

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

    private RentSettingsContract.ViewPresentable presentable;
    private RentSettingsPresenter presenter;

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

        presenter = new RentSettingsPresenter(this,
                NavigatorImpl.newInstance(this),
                DEPENDENCIES.getLogger());
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
    public Property getPropertyFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        }
        return extras.getParcelable(KEY_PROPERTY);
    }

    @OnClick(R.id.description)
    void onDescriptionClicked() {
        presentable.onDescriptionClicked();
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
