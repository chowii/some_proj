package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.RadioGroupView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 21/8/17.
 */

public class FilterRadioGroupViewHolder extends BaseFormViewHolder<RadioGroupView<String>> {
    private final Context context;

    @BindView(R.id.group)
    RadioGroup radioGroup;

    public FilterRadioGroupViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(RadioGroupView model) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);

        for(String radioText : (List<String>)model.getGroup()){
            if(radioGroup.getChildCount() <= model.getSize())
                addRadioButton(layoutParams, radioText);
        }

    }

    private void addRadioButton(LinearLayout.LayoutParams layoutParams, String radioText) {
        RadioButton button = new RadioButton(context);
        button.setText(radioText);
        button.setGravity(Gravity.CENTER);
        radioGroup.addView(button, layoutParams);

        button.setOnCheckedChangeListener((view, isChecked) ->{
            itemMap.putIfAbsent(button.getText().toString(), isChecked);
        });
    }
}
