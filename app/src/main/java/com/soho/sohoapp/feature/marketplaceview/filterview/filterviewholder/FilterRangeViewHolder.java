package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.content.Context;
import android.view.View;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.RangeItem;
import com.soho.sohoapp.home.BaseFormModel;
import com.wefika.horizontalpicker.HorizontalPicker;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 18/8/17.
 */

public class FilterRangeViewHolder extends BaseFormViewHolder<RangeItem> {

    private final Context context;

    @BindView(R.id.picker_from)
    HorizontalPicker pickerFrom;

    @BindView(R.id.picker_to)
    HorizontalPicker pickerTo;


    public FilterRangeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
    }

    @Override
    public void onBindViewHolder(RangeItem model) {
        String s [] = context.getResources().getStringArray(R.array.filter_price_range);
        pickerFrom.setValues(s);
        pickerTo.setValues(s);
        pickerTo.setOnItemSelectedListener(index -> itemMap.put("price_to", s[index]));
        pickerFrom.setOnItemSelectedListener(index -> itemMap.put("price_from", s[index]));
    }

    /*
            field.current_value = holder.scrollPicker.getSelectedItem();

            field.current_value = holder.scrollPicker.getSelectedItem();
            holder.scrollPicker.setOnItemSelectedListener(index -> {
                field.current_value = index;
            });
     */


}
