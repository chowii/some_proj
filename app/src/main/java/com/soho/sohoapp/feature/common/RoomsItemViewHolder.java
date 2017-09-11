package com.soho.sohoapp.feature.common;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.filterviewholder.OnViewHolderItemValueChangeListener;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.PropertyRoomItem;

import java.util.Locale;

import butterknife.BindView;

import static com.soho.sohoapp.SohoApplication.getStringFromResource;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_BY_BATHROOM_COUNT;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_BY_BEDROOM_COUNT;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_BY_CARSPOT_COUNT;

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
        String propertyCrieteriaFormat = getStringFromResource(R.string.property_criteria_button_format_string);
        bedroomAddButton.setOnClickListener((v) -> {
            bedroomValueTextView.setText(String.format(Locale.getDefault(),
                    propertyCrieteriaFormat,
                    ++bedroomValue,
                    getStringFromResource(R.string.rooms_item_bedroom_text)));
            updatedListener.onChange(FILTER_BY_BEDROOM_COUNT, bedroomValue);
        });

        bedroomSubtractButton.setOnClickListener((v) -> {

            if (bedroomValue > 1)bedroomValueTextView.setText(String.format(Locale.getDefault(),
                    propertyCrieteriaFormat,
                    --bedroomValue,
                    getStringFromResource(R.string.rooms_item_bedroom_text)));
            else if (bedroomValue <= 1) bedroomValueTextView.setText(getStringFromResource(R.string.rooms_item_default_bedroom_text));
            updatedListener.onChange(FILTER_BY_BEDROOM_COUNT, bedroomValue);
        });


        bathroomAddButton.setOnClickListener((v) -> {
            bathroomValueTextView.setText(String.format(Locale.getDefault(),
                    propertyCrieteriaFormat,
                    ++bathroomValue,
                    getStringFromResource(R.string.rooms_item_bathroom_text)));
            updatedListener.onChange(FILTER_BY_BATHROOM_COUNT, bathroomValue);
        });

        bathroomSubtractButton.setOnClickListener((v) -> {
            if (bathroomValue > 1) bathroomValueTextView.setText(String.format(Locale.getDefault(),
                    propertyCrieteriaFormat,
                    --bathroomValue,
                    getStringFromResource(R.string.rooms_item_bathroom_text)));
            else if (bathroomValue <= 1) bathroomValueTextView.setText(getStringFromResource(R.string.rooms_item_default_bathroom_text));
            updatedListener.onChange(FILTER_BY_BATHROOM_COUNT, bathroomValue);
        });


        carspotAddButton.setOnClickListener((v) -> {
            carspotValueTextView.setText(String.format(Locale.getDefault(),
                    propertyCrieteriaFormat,
                    ++carspotValue,
                    getStringFromResource(R.string.rooms_item_carspot_text)));
            updatedListener.onChange(FILTER_BY_CARSPOT_COUNT, carspotValue);
        });

        carspotSubtractButton.setOnClickListener((v) -> {
            if (carspotValue > 1) carspotValueTextView.setText(String.format(Locale.getDefault(),
                    propertyCrieteriaFormat,
                    --carspotValue,
                    getStringFromResource(R.string.rooms_item_carspot_text)));
            else if (carspotValue <= 1) carspotValueTextView.setText(getStringFromResource(R.string.rooms_item_default_carspot_text));
            updatedListener.onChange(FILTER_BY_CARSPOT_COUNT, carspotValue);
        });
    }

}
