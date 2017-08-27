package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.CheckboxTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 18/8/17.
 */

public class FilterCheckboxViewHolder extends BaseFormViewHolder<CheckboxTitle> {

    @BindView(R.id.checkbox_title_text_view)
    TextView titleTextBox;

    @BindView(R.id.checkbox)
    CheckBox checkBox;

    private OnCheckChangeListener listener;

    public FilterCheckboxViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public FilterCheckboxViewHolder(View itemView, OnCheckChangeListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(CheckboxTitle model) {
        titleTextBox.setText(model.getTitle());
        checkBox.setChecked(model.getValue());
        checkBox.setOnClickListener((view) -> {
            if(listener != null) listener.onCheckChanged(model.getTitle(), checkBox.isChecked());
            String title = titleTextBox.getText().toString();
            itemMap.putIfAbsent(title, checkBox.isSelected());
        });
    }

    public void setOnCheckedChangeListener(OnCheckChangeListener listener){
        this.listener = listener;
    }

    public interface OnCheckChangeListener {
        void onCheckChanged(String checkboxTitle, boolean isChecked);
    }
}
