package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.ButtonItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 23/8/17.
 */

public class FilterButtonItemViewHolder extends BaseFormViewHolder<ButtonItem> {

    @BindView(R.id.button)
    TextView toggleCheckAll;

    public FilterButtonItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(ButtonItem model) {
        toggleCheckAll.setText(model.getButtonText());
    }
}
