package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseFormModel;

import java.lang.reflect.ParameterizedType;

/**
 * Created by chowii on 22/8/17.
 */

public class ValueSelectorItem<T extends Number> extends BaseFormModel<T> {

    @Override
    protected T getModelValue() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> type = (Class<T>) parameterizedType.getOwnerType();
        try {
            return value == null ? type.newInstance(): value;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    protected void setModelValue(T value) { this.value = value; }

    public T getValue(){ return getModelValue(); }
    public void setValue(T value){ setModelValue(value); }


    @Override
    public int getItemViewType() { return R.layout.item_filter_value_selector; }
}
