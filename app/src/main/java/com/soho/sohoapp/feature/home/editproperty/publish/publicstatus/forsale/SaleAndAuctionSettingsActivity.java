package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus.forsale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.navigator.NavigatorImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.ButterKnife.findById;

public class SaleAndAuctionSettingsActivity extends AbsActivity implements SaleAndAuctionSettingsContract.ViewInteractable {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.sellingOptions)
    RadioGroup sellingOptions;

    @BindView(R.id.auctionAddressOptions)
    RadioGroup auctionAddressOptions;

    @BindView(R.id.inspectionTimeOptions)
    RadioGroup inspectionTimeOptions;

    @BindView(R.id.priceLayout)
    LinearLayout priceLayout;

    @BindView(R.id.auctionAddressLayout)
    LinearLayout auctionAddressLayout;

    @BindView(R.id.auctionDateLayout)
    LinearLayout auctionDateLayout;

    @BindView(R.id.inspectionTimeLayout)
    LinearLayout inspectionTimeLayout;

    @BindView(R.id.propertySizeLayout)
    LinearLayout propertySizeLayout;

    @BindView(R.id.inspectionTimeDesc)
    TextView inspectionTimeDesc;

    @BindView(R.id.saleTitleHeader)
    TextView saleTitleHeader;

    @BindView(R.id.auctionAddress)
    TextView auctionAddress;

    private SaleAndAuctionSettingsContract.ViewPresentable presentable;
    private SaleAndAuctionSettingsPresenter presenter;

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

        presenter = new SaleAndAuctionSettingsPresenter(this, NavigatorImpl.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
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
        auctionDateLayout.setVisibility(View.GONE);
        priceLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOptionsForAuction() {
        saleTitleHeader.setText(R.string.publish_property_auction_title);
        priceLayout.setVisibility(View.GONE);
        auctionAddressLayout.setVisibility(View.VISIBLE);
        auctionDateLayout.setVisibility(View.VISIBLE);
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
    public void showToast(String message) {
        super.showToast(message);
    }

    @Override
    public void showAuctionAddress(String address) {
        auctionAddress.setText(address);
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
