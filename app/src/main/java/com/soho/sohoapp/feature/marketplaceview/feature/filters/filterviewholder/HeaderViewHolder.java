package com.soho.sohoapp.feature.marketplaceview.feature.filters.filterviewholder;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.feature.filters.fitlermodel.HeaderItem;

import butterknife.BindView;

/**
 * Created by chowii on 18/8/17.
 */

public class HeaderViewHolder extends BaseFormViewHolder<HeaderItem> {

    @BindView(R.id.header_text_view)
    TextView headerTextView;

    public HeaderViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindViewHolder(HeaderItem model) {
        headerTextView.setText(model.getHeaderText());
    }
}
