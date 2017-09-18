package com.soho.sohoapp.feature.marketplaceview.components;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.customviews.UserAvatarView;
import com.soho.sohoapp.data.models.BasicProperty;
import com.soho.sohoapp.feature.home.editproperty.ImageHeaderViewPager;

import butterknife.BindView;

/**
 * Created by chowii on 15/8/17.
 */

class PropertyViewHolder extends BaseViewHolder<BasicProperty> {

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

    @BindView(R.id.user_avatar_view)
    UserAvatarView userAvatarView;

    @BindView(R.id.rootView)
    ViewGroup viewView;

    private final OnMarketplaceItemClickListener listener;
    ImageHeaderViewPager pagerAdapter;

    PropertyViewHolder(View itemView, OnMarketplaceItemClickListener listener) {
        super(itemView);
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(BasicProperty property){
        if (pagerAdapter == null)
            pagerAdapter = new ImageHeaderViewPager(imageViewPager.getContext());
        imageViewPager.setAdapter(pagerAdapter);
        pagerAdapter.setData(property.getPhotosAsImages());
        viewView.setOnClickListener(v -> listener.onMarketplaceItemClicked(property.getId()));
        pagerAdapter.setOnItemClickListener(image -> {
            listener.onMarketplaceItemClicked(property.getId());
        });
        userAvatarView.populateWithPropertyUser(property.getRepresentingUser());
        configurePropertyDetails(property);
    }

    private void configurePropertyDetails(BasicProperty property) {
        titleTextView.setText(property.getDisplayTitle());
        streetAddressTextView.setText(property.getLocation().getAddressLine1());
        suburbAddressTextView.setText(property.getLocation().getAddressLine2());
        bedroomTextView.setText(String.valueOf(property.getBedrooms()));
        bathroomTextView.setText(String.valueOf(property.getBathrooms()));
        parkingTextView.setText(String.valueOf(property.getCarspots()));
    }

    interface OnMarketplaceItemClickListener {
        void onMarketplaceItemClicked(int id);
    }

}
