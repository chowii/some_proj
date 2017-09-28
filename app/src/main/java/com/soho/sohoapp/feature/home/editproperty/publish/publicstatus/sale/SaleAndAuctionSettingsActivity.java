package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.sale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static butterknife.ButterKnife.findById;
import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class SaleAndAuctionSettingsActivity extends AbsActivity implements SaleAndAuctionSettingsContract.ViewInteractable {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";
    private static final String TAG_DATE_PICKER_DIALOG = "TAG_DATE_PICKER_DIALOG";
    private static final String TAG_TIME_PICKER_DIALOG = "TAG_TIME_PICKER_DIALOG";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.sellingOptions)
    RadioGroup sellingOptions;

    @BindView(R.id.auctionAddressOptions)
    RadioGroup auctionAddressOptions;

    @BindView(R.id.inspectionTimeOptions)
    RadioGroup inspectionTimeOptions;

    @BindView(R.id.auctionAddressLayout)
    LinearLayout auctionAddressLayout;

    @BindView(R.id.auctionDateLayouts)
    LinearLayout auctionDateLayouts;

    @BindView(R.id.inspectionTimeLayout)
    LinearLayout inspectionTimeLayout;

    @BindView(R.id.inspectionTimeDesc)
    TextView inspectionTimeDesc;

    @BindView(R.id.saleTitleHeader)
    TextView saleTitleHeader;

    @BindView(R.id.auctionAddress)
    TextView auctionAddress;

    @BindView(R.id.auctionDate)
    TextView auctionDate;

    @BindView(R.id.auctionTime)
    TextView auctionTime;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.title)
    EditText title;

    @BindView(R.id.priceValue)
    EditText priceValue;

    @BindView(R.id.priceIndicator)
    ImageView priceIndicator;

    private SaleAndAuctionSettingsContract.ViewPresentable presentable;
    private SaleAndAuctionSettingsPresenter presenter;
    private LoadingDialog loadingDialog;

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull Property property) {
        Intent intent = new Intent(context, SaleAndAuctionSettingsActivity.class);
        intent.putExtra(KEY_PROPERTY, property);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_and_auction_settings);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(view -> presentable.onBackClicked());
        initView();

        presenter = new SaleAndAuctionSettingsPresenter(this,
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
        Bundle extras = getExtras(data);
        if (resultCode == Activity.RESULT_OK && extras != null) {
            switch (requestCode) {
                case RequestCode.PROPERTY_AUCTION_ADDRESS:
                    presentable.onAuctionAddressSelected(extras.getParcelable(NavigatorImpl.KEY_PROPERTY_LOCATION));
                    break;
                case RequestCode.PROPERTY_SALE_SETTINGS_DESCRIPTION:
                    presentable.onDescriptionChanged(extras.getString(NavigatorImpl.KEY_STRING));
                    break;
            }
        }
    }

    @Override
    public void setPresentable(SaleAndAuctionSettingsContract.ViewPresentable presentable) {
        this.presentable = presentable;
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
    public void showOptionsForSale() {
        saleTitleHeader.setText(R.string.publish_property_sale_title);
        auctionAddressLayout.setVisibility(View.GONE);
        auctionDateLayouts.setVisibility(View.GONE);
    }

    @Override
    public void showOptionsForAuction() {
        saleTitleHeader.setText(R.string.publish_property_auction_title);
        auctionAddressLayout.setVisibility(View.VISIBLE);
        auctionDateLayouts.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableAuctionAddress(boolean enable) {
        auctionAddress.setEnabled(enable);
        if (enable) {
            auctionAddress.setTextColor(getResources().getColor(R.color.primaryText));
        } else {
            auctionAddress.setTextColor(getResources().getColor(R.color.hintText));
        }
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
    public void showToastMessage(@StringRes int resId) {
        super.showToast(resId);
    }

    @Override
    public void showError(@NonNull Throwable throwable) {
        handleError(throwable);
    }

    @Override
    public void showAuctionAddress(String address) {
        auctionAddress.setText(address);
    }

    @Override
    public void showAuctionDate(String date) {
        auctionDate.setText(date);
    }

    @Override
    public void showAuctionTime(String time) {
        auctionTime.setText(time);
    }

    @Override
    public void showDefaultAuctionAddressDesc() {
        auctionAddress.setText(R.string.publish_property_auction_address_desc);
    }

    @Override
    public void showTimePicker(Calendar calendar, TimePickerDialog.OnTimeSetListener listener) {
        TimePickerDialog timePicker = TimePickerDialog.newInstance(listener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), false);
        timePicker.show(getFragmentManager(), TAG_TIME_PICKER_DIALOG);
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
    public String getListingTitle() {
        return title.getText().toString();
    }

    @Override
    public String getPriceValue() {
        return priceValue.getText().toString();
    }

    @Override
    public boolean isForSaleChecked() {
        return R.id.leftOption == sellingOptions.getCheckedRadioButtonId();
    }

    @Override
    public boolean isOffSiteLocationChecked() {
        return R.id.rightOption == auctionAddressOptions.getCheckedRadioButtonId();
    }

    @Override
    public void selectAuctionOption() {
        RadioButton auctionOption = findById(sellingOptions, R.id.rightOption);
        auctionOption.setChecked(true);
    }

    @Override
    public void showTitle(String title) {
        this.title.setText(title);
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
    public void showPriceGuide(double value) {
        priceValue.setText(String.valueOf(value));
    }

    @Override
    public void showDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void selectOffSiteAuctionOption() {
        RadioButton offSiteLocation = findById(auctionAddressOptions, R.id.rightOption);
        offSiteLocation.setChecked(true);
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


    @OnClick(R.id.description)
    void onDescriptionClicked() {
        presentable.onDescriptionClicked();
    }

    @OnClick(R.id.auctionAddress)
    void onAuctionAddressClicked() {
        presentable.onAuctionAddressClicked();
    }

    @OnClick(R.id.inspectionTimeLayout)
    void onInspectionTimeClicked() {
        presentable.onInspectionTimeClicked();
    }

    @OnClick(R.id.propertySizeLayout)
    void onPropertySizeClicked() {
        presentable.onPropertySizeClicked();
    }

    @OnClick(R.id.auctionDateLayout)
    void onAuctionDateClicked() {
        presentable.onAuctionDateClicked();
    }

    @OnClick(R.id.auctionTimeLayout)
    void onAuctionTimeClicked() {
        presentable.onAuctionTimeClicked();
    }

    @OnClick(R.id.save)
    void onSaveClicked() {
        presentable.onSaveClicked();
    }

    @OnTextChanged(R.id.priceValue)
    void onPriceChanged(CharSequence text) {
        presentable.onPriceChanged(text.toString());
    }

    @Nullable
    private Bundle getExtras(Intent data) {
        return data != null ? data.getExtras() : null;
    }

    private void initView() {
        RadioButton saleOption = findById(sellingOptions, R.id.leftOption);
        saleOption.setText(R.string.publish_property_sale);
        RadioButton auctionOption = findById(sellingOptions, R.id.rightOption);
        auctionOption.setText(R.string.publish_property_auction);

        RadioButton onSiteOption = findById(auctionAddressOptions, R.id.leftOption);
        onSiteOption.setText(R.string.publish_property_auction_on_site);
        RadioButton offSiteOption = findById(auctionAddressOptions, R.id.rightOption);
        offSiteOption.setText(R.string.publish_property_auction_off_site);

        RadioButton setTimeOption = findById(inspectionTimeOptions, R.id.leftOption);
        setTimeOption.setText(R.string.publish_property_set_time);
        RadioButton appointmentOption = findById(inspectionTimeOptions, R.id.rightOption);
        appointmentOption.setText(R.string.publish_property_appointment_only);

        sellingOptions.setOnCheckedChangeListener((radioGroup, i) ->
        {
            switch (i) {
                case R.id.leftOption:
                    presentable.onSaleOptionClicked();
                    break;
                case R.id.rightOption:
                    presentable.onAuctionOptionClicked();
                    break;
            }
        });

        auctionAddressOptions.setOnCheckedChangeListener((radioGroup, i) ->
        {
            switch (i) {
                case R.id.leftOption:
                    presentable.onOnSiteClicked();
                    break;
                case R.id.rightOption:
                    presentable.onOffSiteClicked();
                    break;
            }
        });

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
