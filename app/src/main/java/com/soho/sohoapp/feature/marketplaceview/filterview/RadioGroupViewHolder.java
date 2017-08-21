package com.soho.sohoapp.feature.marketplaceview.filterview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.RadioGroupView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 21/8/17.
 */

class RadioGroupViewHolder extends BaseViewHolder<RadioGroupView> {
    private final Context context;

    @BindView(R.id.group)
    RadioGroup group;


    RadioGroupViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(RadioGroupView model) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);

        model.getGroup().stream().filter(
                radioText -> group.getChildCount() <= model.getSize()).forEach(radioText ->
            addRadioButton(layoutParams, radioText)
        );
    }

    private void addRadioButton(LinearLayout.LayoutParams layoutParams, String radioText) {
        RadioButton button = new RadioButton(context);
        button.setText(radioText);
        button.setGravity(Gravity.CENTER);
        button.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.seperator,
                R.drawable.seperator,
                R.drawable.seperator,
                R.drawable.seperator
        );
        group.addView(button, layoutParams);
    }
}
