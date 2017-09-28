package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.navigator.RequestCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.ButterKnife.findById;

public class PublicStatusSettingsActivity extends AbsActivity implements PublicStatusSettingsContract.ViewInteractable {
    private static final String KEY_PROPERTY = "KEY_PROPERTY";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.discoverable)
    LinearLayout discoverable;

    @BindView(R.id.saleAndAuction)
    LinearLayout saleAndAuction;

    @BindView(R.id.rent)
    LinearLayout rent;

    private PublicStatusSettingsContract.ViewPresentable presentable;
    private PublicStatusSettingsPresenter presenter;

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull Property property) {
        Intent intent = new Intent(context, PublicStatusSettingsActivity.class);
        intent.putExtra(KEY_PROPERTY, property);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_public_status);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(view -> presentable.onBackClicked());
        initView();

        presenter = new PublicStatusSettingsPresenter(this, NavigatorImpl.newInstance(this));
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
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCode.PROPERTY_PUBLIC_STATUS_UPDATE) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                presentable.onPropertyStatusUpdated(extras.getParcelable(NavigatorImpl.KEY_PROPERTY));
            }
        }
    }

    @Override
    public void setPresentable(PublicStatusSettingsContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public void showDiscoverableIndicator() {
        showIndicator(discoverable);
    }

    @Override
    public void hideDiscoverableIndicator() {
        hideIndicator(discoverable);
    }

    @Override
    public void showSaleAndAuctionIndicator() {
        showIndicator(saleAndAuction);
    }

    @Override
    public void hideSaleAndAuctionIndicator() {
        hideIndicator(saleAndAuction);
    }

    @Override
    public void showRentIndicator() {
        showIndicator(rent);
    }

    @Override
    public void hideRentIndicator() {
        hideIndicator(rent);
    }

    @Override
    public Property getPropertyFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        }
        return extras.getParcelable(KEY_PROPERTY);
    }

    @OnClick(R.id.discoverable)
    public void onDiscoverableClicked() {
        presentable.onDiscoverableClicked();
    }

    @OnClick(R.id.saleAndAuction)
    public void onSaleAndAuctionClicked() {
        presentable.onSaleAndAuctionClicked();
    }

    @OnClick(R.id.rent)
    public void onRentClicked() {
        presentable.onRentClicked();
    }

    private void showIndicator(LinearLayout layout) {
        findById(layout, R.id.arrow).setVisibility(View.GONE);
        findById(layout, R.id.indicator).setVisibility(View.VISIBLE);
        findById(layout, R.id.update).setVisibility(View.VISIBLE);
    }

    private void hideIndicator(LinearLayout layout) {
        findById(layout, R.id.indicator).setVisibility(View.INVISIBLE);
        findById(layout, R.id.update).setVisibility(View.GONE);
        findById(layout, R.id.arrow).setVisibility(View.VISIBLE);
    }

    private void initView() {
        TextView discoverableTitle = findById(discoverable, R.id.title);
        discoverableTitle.setText(R.string.publish_property_discoverable);
        TextView discoverableDesc = findById(discoverable, R.id.description);
        discoverableDesc.setText(R.string.publish_property_discoverable_desc);

        TextView saleAndAuctionTitle = findById(saleAndAuction, R.id.title);
        saleAndAuctionTitle.setText(R.string.publish_property_sale_and_auction);
        TextView saleAndAuctionDesc = findById(saleAndAuction, R.id.description);
        saleAndAuctionDesc.setText(R.string.publish_property_sale_and_auction_desc);

        TextView rentTitle = findById(rent, R.id.title);
        rentTitle.setText(R.string.publish_property_rent);
        TextView rentDesc = findById(rent, R.id.description);
        rentDesc.setText(R.string.publish_property_rent_decs);
    }
}
