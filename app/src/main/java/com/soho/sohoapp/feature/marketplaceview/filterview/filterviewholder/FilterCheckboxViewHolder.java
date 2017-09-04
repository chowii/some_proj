package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.FilterCheckboxItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 18/8/17.
 */

public class FilterCheckboxViewHolder extends BaseFormViewHolder<FilterCheckboxItem> {

    @BindView(R.id.checkbox_title_text_view)
    TextView titleTextBox;

    @BindView(R.id.checkbox)
    CheckBox checkBox;

    private OnCheckChangeListener listener;

    public FilterCheckboxViewHolder(View itemView, OnViewHolderItemValueChangeListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        updatedListener = listener;
    }

    @Override
    public void onBindViewHolder(FilterCheckboxItem model) {
        titleTextBox.setText(model.getTitle());
        checkBox.setChecked(model.getValue());
        checkBox.setOnClickListener((view) -> {
            if(listener != null) listener.onCheckChanged(model.getTitle(), checkBox.isChecked());
            updatedListener.onChange("by_property_types", model.getKey());
        });
    }

    public void setOnCheckedChangeListener(OnCheckChangeListener listener){
        this.listener = listener;
    }

    public void setOnViewHolderItemUpdateListener(OnViewHolderItemValueChangeListener listener){
        updatedListener = listener;
    }

    public interface OnCheckChangeListener {
        void onCheckChanged(String checkboxTitle, boolean isChecked);
    }
}