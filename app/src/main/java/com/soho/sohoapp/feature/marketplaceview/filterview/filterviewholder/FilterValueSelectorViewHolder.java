package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.ValueSelectorItem;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        ButterKnife.bind(this, itemView);
        updatedListener = listener;
    }

    @Override
    public void onBindViewHolder(ValueSelectorItem model) {
        subtractButton.setOnClickListener((v) ->{
            if(radiusValue > 1)valueTextView.setText(--radiusValue + "km");
        });

        addButton.setOnClickListener((v) -> {
            valueTextView.setText(++radiusValue + "km");
            updatedListener.onChange("by_google_places[distance]", radiusValue * 1_000);
        });

        subtractButton.setOnClickListener((v) -> {
            valueTextView.setText(++radiusValue + "km");
            updatedListener.onChange("by_google_places[distance]", radiusValue * 1_000);
        });
    }
}
