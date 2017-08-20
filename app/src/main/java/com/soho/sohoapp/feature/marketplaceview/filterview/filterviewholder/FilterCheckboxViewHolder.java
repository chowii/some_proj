package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.CheckboxTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 18/8/17.
 */

public class FilterCheckboxViewHolder extends BaseViewHolder<CheckboxTitle> {

    @BindView(R.id.checkbox_title_text_view)
    TextView titleTextBox;

    public FilterCheckboxViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(CheckboxTitle model) {
        titleTextBox.setText(model.getTitle());
    }
}
