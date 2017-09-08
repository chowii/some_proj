package com.soho.sohoapp.feature.marketplaceview.feature.filterview.filterviewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.ValueSelectorItem;

import java.util.Locale;

import butterknife.BindView;

import static com.soho.sohoapp.network.Keys.Filter.FILTER_BY_GOOGLE_PLACES;

/**
 * Created by chowii on 18/8/17.
 */

public class FilterValueSelectorViewHolder extends BaseFormViewHolder<ValueSelectorItem> {

    @BindView(R.id.subtract_button)
    Button subtractButton;

    @BindView(R.id.value_text_view)
    TextView valueTextView;

    @BindView(R.id.add_button)
    Button addButton;

    private byte radiusValue;

    public FilterValueSelectorViewHolder(View itemView, OnViewHolderItemValueChangeListener listener) {
        super(itemView);
        updatedListener = listener;
    }

    @Override
    public void onBindViewHolder(ValueSelectorItem model) {
        addButton.setOnClickListener((v) -> {
            valueTextView.setText(String.format(Locale.getDefault(),"%dkm", ++radiusValue));
            updatedListener.onChange(FILTER_BY_GOOGLE_PLACES, radiusValue * 1_000);
        });

        subtractButton.setOnClickListener((v) -> {
            if(radiusValue > 1)
                valueTextView.setText(String.format(Locale.getDefault(),"%dkm", --radiusValue));
            updatedListener.onChange(FILTER_BY_GOOGLE_PLACES, radiusValue * 1_000);
        });
    }
}
