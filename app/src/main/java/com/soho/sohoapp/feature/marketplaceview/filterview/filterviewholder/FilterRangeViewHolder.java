package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.RangeItem;
import com.wefika.horizontalpicker.HorizontalPicker;

import butterknife.BindView;

/**
 * Created by chowii on 18/8/17.
 */

public class FilterRangeViewHolder extends BaseFormViewHolder<RangeItem> {

    @BindView(R.id.picker_from)
    HorizontalPicker pickerFrom;

    @BindView(R.id.picker_to)
    HorizontalPicker pickerTo;

    private final Context context;
    private final boolean mIsBuySection;

    public FilterRangeViewHolder(View itemView, OnViewHolderItemValueChangeListener listener, boolean isBuySection) {
        super(itemView);
        context = itemView.getContext();
        updatedListener = listener;
        mIsBuySection = isBuySection;
    }

    @Override
    public void onBindViewHolder(RangeItem model) {
        String[] pickerValues;
        if(mIsBuySection) pickerValues = initBuyPickerItem();
        else pickerValues = initRentPickerItem();
        invokeListenerOnInit(mIsBuySection,pickerValues);
        pickerFrom.setValues(pickerValues);
        pickerTo.setValues(pickerValues);
    }

    @NonNull
    private String[] initBuyPickerItem() {
        String[] pickerValues;
        pickerValues = context.getResources().getStringArray(R.array.filter_buy_price_range);
        setBuyListener(pickerValues);
        return pickerValues;
    }

    private void setBuyListener(String[] pickerValues) {
        pickerFrom.setOnItemSelectedListener(index -> updatedListener.onChange("by_min_sell_price", pickerValues[index]));
        pickerTo.setOnItemSelectedListener(index -> updatedListener.onChange("by_max_sell_price", pickerValues[index]));
    }

    @NonNull
    private String[] initRentPickerItem() {
        String[] pickerValues;
        pickerValues = context.getResources().getStringArray(R.array.filter_rent_price_range);
        setRentListener(pickerValues);
        return pickerValues;
    }

    private void setRentListener(String[] pickerValues) {
        pickerFrom.setOnItemSelectedListener(index -> updatedListener.onChange("by_min_rent_price", pickerValues[index]));
        pickerTo.setOnItemSelectedListener(index -> updatedListener.onChange("by_max_rent_price", pickerValues[index]));
    }

    private void invokeListenerOnInit(boolean isBuySection, String[] pickerValues){
        if(isBuySection){
            updatedListener.onChange("by_min_sell_price", pickerValues[0]);
            updatedListener.onChange("by_max_sell_price", pickerValues[0]);
        }else{
            updatedListener.onChange("by_min_rent_price", pickerValues[0]);
            updatedListener.onChange("by_max_rent_price", pickerValues[0]);
        }
    }

}
