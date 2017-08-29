package com.soho.sohoapp.feature.marketplaceview.filterview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.FilterButtonItemViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.FilterCheckboxViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.FilterRadioGroupViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.FilterRangeViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.FilterToggleItemViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.FilterValueSelectorViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.HeaderViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.RecyclerViewViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.TextSearchViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.OnViewHolderItemValueChangeListener;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.FilterCheckboxItem;
import com.soho.sohoapp.helper.FileWriter;
import com.soho.sohoapp.home.BaseFormModel;

import org.json.JSONObject;

import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chowii on 18/8/17.
 */

class PropertyFilterAdapter extends RecyclerView.Adapter<BaseFormViewHolder> implements OnViewHolderItemValueChangeListener {

    private final Context context;
    private final Map<String, Object> mFilterMap;
    private final boolean mIsBuySection;
    List<? extends BaseFormModel> mFilterItems;
    FilterCheckboxViewHolder view;

    PropertyFilterAdapter(List<? extends BaseFormModel> filterItems, Context context, boolean isBuySection) {
        this.mFilterItems = filterItems;
        this.context = context;
        mFilterMap = new HashMap<>();
        this.mIsBuySection = isBuySection;
    }

    @Override
    public int getItemCount() {
        return mFilterItems.size();
    }

    @Override
    public int getItemViewType(int position) { return mFilterItems.get(position).getItemViewType(); }

    @Override
    public void onViewDetachedFromWindow(BaseFormViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public BaseFormViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        switch (viewType){
            case R.layout.item_filter_header:
                return new HeaderViewHolder(itemView);
            case R.layout.item_filter_search:
                return new TextSearchViewHolder(itemView);
            case R.layout.item_filter_value_selector:
                return new FilterValueSelectorViewHolder(itemView, this);
            case R.layout.item_filter_range:
                return new FilterRangeViewHolder(itemView, this, mIsBuySection);
            case R.layout.item_filter_checkbox:
                return new FilterCheckboxViewHolder(itemView, this);
            case R.layout.item_filter_test:
                return new RecyclerViewViewHolder(itemView);
            case R.layout.item_radio_group:
                return new FilterRadioGroupViewHolder(itemView, this);
            case R.layout.item_filter_toggle:
                return new FilterToggleItemViewHolder(itemView, this);
            case R.layout.item_button:
            case R.layout.item_favourite_button:
                return new FilterButtonItemViewHolder(itemView, this);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseFormViewHolder holder, int position) {
        holder.onBindViewHolder(mFilterItems.get(position));
        addCheckboxAction(holder);
        if(holder instanceof FilterButtonItemViewHolder){
            FilterButtonItemViewHolder buttonViewHolder = (FilterButtonItemViewHolder) holder;
            buttonViewHolder.setOnSaveFilterPreferenceListener(() -> {
                Map<String, Object> map = new HashMap<>();
                JSONObject json = new JSONObject(mFilterMap);
                FileWriter.createDeviceFile(context, json.toString());
            });
        }
    }

    private void addCheckboxAction(BaseFormViewHolder holder) {
        if(holder instanceof FilterCheckboxViewHolder){
            view = (FilterCheckboxViewHolder) holder;

            view.setOnCheckedChangeListener((title, isChecked) -> {
                for(BaseFormModel modelItem: mFilterItems)
                    if(modelItem instanceof FilterCheckboxItem)
                        configureCheckboxAction((FilterCheckboxItem) modelItem, title, isChecked);
                notifyDataSetChanged();
            });
        }
    }

    private void configureCheckboxAction(FilterCheckboxItem modelItem, String title, boolean isChecked) {
        if(title.equalsIgnoreCase("All")) checkedAll(modelItem);
        else checkedIndividual(modelItem, title, isChecked);
    }

    private void checkedAll(FilterCheckboxItem item) {
        boolean isAll = item.getTitle().equalsIgnoreCase("All");
        if(!isAll) item.setValue(false);
        else item.setValue(true);
    }

    private void checkedIndividual(FilterCheckboxItem item, String title, boolean isChecked) {
        boolean isAll = item.getTitle().equalsIgnoreCase("All");
        if(isAll) item.setValue(false);
        else if(item.getTitle().equalsIgnoreCase(title)) item.setValue(isChecked);
    }

    @Override
    public void onChange(CharSequence key, Object value) {
        if(value instanceof AbstractList) addListToFilterMap((List<String>) value);
        else mFilterMap.put(key.toString(), value);
        Log.d("LOG_TAG---", "onChange: " + mFilterMap.toString());
    }

    private void addListToFilterMap(List<String> value) {
        List<String> list = value;
        List<String> item = (List<String>) mFilterMap.get("by_property_type");
        if(item == null) mFilterMap.put("by_property_type", list);
        else {
            item.addAll(list);
            mFilterMap.put("by_property_type", item);
        }
    }
}
