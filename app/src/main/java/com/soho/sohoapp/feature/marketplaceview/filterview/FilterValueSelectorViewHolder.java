package com.soho.sohoapp.feature.marketplaceview.filterview;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 18/8/17.
 */

class FilterValueSelectorViewHolder extends BaseViewHolder {


    @BindView(R.id.subtract_button)
    Button subtractButton;

    @BindView(R.id.value_text_view)
    TextView valueTextView;

    @BindView(R.id.add_button)
    Button addButton;

    private byte radiusValue;

    FilterValueSelectorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(BaseModel model) {
        subtractButton.setOnClickListener((v) ->{
            if(radiusValue > 1)valueTextView.setText(--radiusValue + "km");
        });

        addButton.setOnClickListener((v) -> valueTextView.setText(++radiusValue + "km"));
    }
}
