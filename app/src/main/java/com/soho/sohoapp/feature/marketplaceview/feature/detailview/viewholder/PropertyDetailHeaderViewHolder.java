package com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyDetailHeaderItem;

import butterknife.BindView;

/**
 * Created by chowii on 1/9/17.
 */

public class PropertyDetailHeaderViewHolder extends BaseViewHolder<PropertyDetailHeaderItem> {

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.property_type_text_view)
    TextView typeTextView;

    @BindView(R.id.bedroom_text_view)
    TextView bedroomTextView;

    @BindView(R.id.bathroom_text_view)
    TextView bathroomTextView;

    @BindView(R.id.parking_text_view)
    TextView parkingTextView;

    @BindView(R.id.property_size_text_view)
    TextView propertySizeTextView;

    public PropertyDetailHeaderViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindViewHolder(PropertyDetailHeaderItem model) {
        titleTextView.setText(model.getHeader());
        typeTextView.setText(model.retrievePropertyType());
        bedroomTextView.setText(String.valueOf(model.getBedroom()));
        bathroomTextView.setText(String.valueOf(model.getBathroom()));
        parkingTextView.setText(String.valueOf(model.getCarspot()));
        propertySizeTextView.setText(model.getPropertySizeWithUnit());
    }
}
