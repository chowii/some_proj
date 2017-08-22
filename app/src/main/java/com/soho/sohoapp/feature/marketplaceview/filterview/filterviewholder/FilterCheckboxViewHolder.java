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
    public TextView titleTextBox;

    @BindView(R.id.checkbox)
    public CheckBox checkBox;

    public FilterCheckboxViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(CheckboxTitle model) {
        titleTextBox.setText(model.getTitle());
        checkBox.setChecked(model.getValue());
    }
}
