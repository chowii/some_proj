package com.soho.sohoapp.feature.common;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.filterviewholder.OnViewHolderItemValueChangeListener;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.PropertyRoomItem;

import butterknife.BindView;

/**
 * Created by chowii on 31/8/17.
 */

public class RoomsItemViewHolder extends BaseFormViewHolder<PropertyRoomItem> {

    @BindView(R.id.bedroom_value_text_view)
    TextView bedroomValueTextView;

    @BindView(R.id.bathroom_value_text_view)
    TextView bathroomValueTextView;

    @BindView(R.id.carspot_value_text_view)
    TextView carspotValueTextView;

    @BindView(R.id.bedroom_add_button)
    Button bedroomAddButton;

    @BindView(R.id.bedroom_subtract_button)
    Button bedroomSubtractButton;

    @BindView(R.id.bathroom_add_button)
    Button bathroomAddButton;

    @BindView(R.id.bathroom_subtract_button)
    Button bathroomSubtractButton;

    @BindView(R.id.carspot_add_button)
    Button carspotAddButton;

    @BindView(R.id.carspot_subtract_button)
    Button carspotSubtractButton;

    private final Context context;

    private byte bedroomValue;
    private byte bathroomValue;
    private byte carspotValue;

    public RoomsItemViewHolder(View itemView, OnViewHolderItemValueChangeListener listener) {
        super(itemView);
        updatedListener = listener;
        context = itemView.getContext();
    }

    @Override
    public void onBindViewHolder(PropertyRoomItem model) {

        bedroomAddButton.setOnClickListener((v) -> {
            bedroomValueTextView.setText(++bedroomValue + " " + getString(R.string.rooms_item_bedroom_text));
            updatedListener.onChange("by_bedroom_count", bedroomValue);
        });

        bedroomSubtractButton.setOnClickListener((v) -> {
            if(bedroomValue > 1)bedroomValueTextView.setText(--bedroomValue +" " +  getString(R.string.rooms_item_bedroom_text));
            else if(bedroomValue <= 1) bedroomValueTextView.setText("Any " + getString(R.string.rooms_item_bedroom_text));
            updatedListener.onChange("by_bedroom_count", bedroomValue);
        });


        bathroomAddButton.setOnClickListener((v) -> {
            bathroomValueTextView.setText(++bathroomValue  + " " + getString(R.string.rooms_item_bathroom_text));
            updatedListener.onChange("by_bathroom_count", bathroomValue);
        });

        bathroomSubtractButton.setOnClickListener((v) -> {
            if(bathroomValue > 1) bathroomValueTextView.setText(--bathroomValue +  " " + getString(R.string.rooms_item_bathroom_text));
            else if(bathroomValue <= 1) bathroomValueTextView.setText("Any " + getString(R.string.rooms_item_bathroom_text));

            updatedListener.onChange("by_bathroom_count", bathroomValue);
        });


        carspotAddButton.setOnClickListener((v) -> {
            carspotValueTextView.setText(++carspotValue  + " " + getString(R.string.rooms_item_carspot_text));
            updatedListener.onChange("by_carspot_count", carspotValue);
        });

        carspotSubtractButton.setOnClickListener((v) -> {
            if(carspotValue > 1) carspotValueTextView.setText(--carspotValue + " " + getString(R.string.rooms_item_carspot_text));
            else if(carspotValue <= 1) carspotValueTextView.setText("Any "  + getString(R.string.rooms_item_carspot_text));

            updatedListener.onChange("by_carspot_count", carspotValue);
        });
    }

    private String getString(@StringRes int stringResource){ return context.getString(stringResource); }

}
