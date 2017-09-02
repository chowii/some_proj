package com.soho.sohoapp.feature.common;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.model.EmptyDataSetItem;

import butterknife.BindView;

/**
 * Created by chowii on 30/8/17.
 */

public class EmptyItemViewHolder extends BaseViewHolder<EmptyDataSetItem> {

    @BindView(R.id.header)
    TextView header;

    @BindView(R.id.subheader)
    TextView subHeader;

    public EmptyItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindViewHolder(EmptyDataSetItem model) {
//        header.setText(model.header());
//        subHeader.setText(model.subHeader());
    }
}
