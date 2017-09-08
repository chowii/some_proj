package com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyDetailDescriptionItem;

import butterknife.BindView;

/**
 * Created by chowii on 1/9/17.
 */

public class PropertyDetailDescriptionViewHolder extends BaseViewHolder<PropertyDetailDescriptionItem> {

    @BindView(R.id.description_text_view)
    TextView descriptionTextView;

    public PropertyDetailDescriptionViewHolder(View itemView) { super(itemView); }

    @Override
    public void onBindViewHolder(PropertyDetailDescriptionItem model) {
        descriptionTextView.setText(model.getDescription());

    }
}
