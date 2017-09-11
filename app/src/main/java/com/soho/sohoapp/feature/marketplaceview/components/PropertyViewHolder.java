package com.soho.sohoapp.feature.marketplaceview.components;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.data.SohoProperty;

import java.util.Locale;

import butterknife.BindView;

/**
 * Created by chowii on 15/8/17.
 */

class PropertyViewHolder extends BaseViewHolder<SohoProperty> {

    @BindView(R.id.image_view_pager)
    ViewPager imageViewPager;

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.street_address_text_view)
    TextView streetAddressTextView;

    @BindView(R.id.suburb_address_text_view)
    TextView suburbAddressTextView;

    @BindView(R.id.bedroom_text_view)
    TextView bedroomTextView;

    @BindView(R.id.bathroom_text_view)
    TextView bathroomTextView;

    @BindView(R.id.parking_text_view)
    TextView parkingTextView;

    @BindView(R.id.rootView)
    ViewGroup viewView;

    private final OnMarketplaceItemClickListener listener;
    PropertyPagerAdapter pagerAdapter;

    PropertyViewHolder(View itemView, OnMarketplaceItemClickListener listener) {
        super(itemView);
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(SohoProperty property){
        if (pagerAdapter == null)
            pagerAdapter = new PropertyPagerAdapter(imageViewPager.getContext());
        imageViewPager.setAdapter(pagerAdapter);
        viewView.setOnClickListener(v -> listener.onMarketplaceItemClicked(property.id()));
        configurePropertyDetails(property);
    }

    private void configurePropertyDetails(SohoProperty property) {
        titleTextView.setText(property.title());
        streetAddressTextView.setText(property.location().retrieveAddress1());
        suburbAddressTextView.setText(getSuburbString(property));
        bedroomTextView.setText(String.valueOf(property.numberOfBedrooms()));
        bathroomTextView.setText(String.valueOf(property.numberOfBathrooms()));
        parkingTextView.setText(String.valueOf(property.numberOfParking()));

    }

    @NonNull
    private String getSuburbString(SohoProperty property) {
        return String.format(
                            Locale.getDefault(),
                            "%s %s, %s",
                            property.location().retrieveSuburb(),
                            property.location().retrievePostcode(),
                            property.location().retrieveState());
    }

    interface OnMarketplaceItemClickListener {
        void onMarketplaceItemClicked(int id);
    }

}
