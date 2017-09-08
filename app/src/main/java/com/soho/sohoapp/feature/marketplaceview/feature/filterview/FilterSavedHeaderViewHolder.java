package com.soho.sohoapp.feature.marketplaceview.feature.filterview;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseModel;

import butterknife.BindView;

/**
 * Created by chowii on 25/8/17.
 */

class FilterSavedHeaderViewHolder extends BaseViewHolder {

    @BindView(R.id.header_text_view)
    TextView headerTextView;

    public FilterSavedHeaderViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindViewHolder(BaseModel model) {
        headerTextView.setText("Saved Search");
    }
}
