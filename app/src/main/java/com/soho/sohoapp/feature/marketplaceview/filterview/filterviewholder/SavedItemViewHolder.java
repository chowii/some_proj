package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.savedfilters.model.SavedFilterItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 30/8/17.
 */

public class SavedItemViewHolder extends BaseViewHolder<SavedFilterItem> {

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.subtitle_text_view)
    TextView subtitleTextView;

    public SavedItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(SavedFilterItem model) {
        titleTextView.setText(model.getTitle());
        subtitleTextView.setText(model.getSubTitle());
    }
}
