package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.ToggleItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 23/8/17.
 */

public class FilterToggleItemViewHolder extends BaseFormViewHolder<ToggleItem> {

    @BindView(R.id.toggle_button)
    SwitchCompat toggleButton;

    public FilterToggleItemViewHolder(View itemView, OnViewHolderItemValueChangeListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        updatedListener = listener;
    }

    @Override
    public void onBindViewHolder(ToggleItem model) {
        toggleButton.setText(model.getText());
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) ->
                updatedListener.onChange("include_surrounding_suburbs", isChecked)
        );
    }
}
