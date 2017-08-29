package com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseFormModel;

import java.util.List;

/**
 * Created by chowii on 21/8/17.
 */

public class RadioGroupView<T> extends BaseFormModel<T> {

    List<T> group;
    private boolean isAdded;
    private int mIndex;

    private RadioGroupView(){}

    public RadioGroupView(List<T> object) {
        group = object;
    }

    public List<T> getGroup() {
        return group;
    }

    public int getSize(){ return group.size(); }

    @Override
    protected T getModelValue() { return group.get(mIndex); }

    @Override
    protected void setModelValue(T value) { isAdded = group.add(value); }

    public T get(int index){
        mIndex = index;
        T value = getModelValue();
        mIndex = -1;
        return value;
    }

    public boolean add(T item){
        setModelValue(item);
        return isAdded;
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_radio_group;
    }
}
