package com.soho.sohoapp.feature.marketplaceview.components;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.model.SohoProperty;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    PropertyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    PropertyPagerAdapter pagerAdapter;

    @Override
    public void onBindViewHolder(SohoProperty property){
        if(pagerAdapter == null)
            pagerAdapter = new PropertyPagerAdapter(imageViewPager.getContext());
        imageViewPager.setAdapter(pagerAdapter);

        configurePropertyDetails(property);
    }

    private void configurePropertyDetails(SohoProperty property) {
        titleTextView.setText(property.title());
        streetAddressTextView.setText(property.location("address_1").toString());

        suburbAddressTextView.setText(getSuburbString(property));

        bedroomTextView.setText(property.numberOfBedrooms() + "");
        bathroomTextView.setText(property.numberOfBathrooms() + "");
        parkingTextView.setText(property.numberOfParking() + "");
    }

    @NonNull
    private String getSuburbString(SohoProperty property) {
        String suburbAddress = property.location("suburb").toString();
        suburbAddress = suburbAddress + " " + property.location("postcode").toString();
        suburbAddress = suburbAddress + ", " + property.location("state").toString();
        return suburbAddress;
    }

}
