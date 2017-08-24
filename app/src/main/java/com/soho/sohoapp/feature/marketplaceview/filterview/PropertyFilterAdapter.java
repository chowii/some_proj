package com.soho.sohoapp.feature.marketplaceview.filterview;

import android.support.v7.widget.RecyclerView;
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
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.CheckboxTitle;
import com.soho.sohoapp.home.BaseFormModel;

import java.util.List;

/**
 * Created by chowii on 18/8/17.
 */

class PropertyFilterAdapter extends RecyclerView.Adapter<BaseFormViewHolder> {

    List<? extends BaseFormModel> filterItems;

    PropertyFilterAdapter(List<? extends BaseFormModel> filterItems) {
        this.filterItems = filterItems;
    }

    @Override
    public int getItemCount() {
        return filterItems.size();
    }

    @Override
    public int getItemViewType(int position) { return filterItems.get(position).getItemViewType(); }

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
                return new FilterValueSelectorViewHolder(itemView);
            case R.layout.item_filter_range:
                return new FilterRangeViewHolder(itemView);
            case R.layout.item_filter_checkbox:
                return new FilterCheckboxViewHolder(itemView);
            case R.layout.item_filter_test:
                return new RecyclerViewViewHolder(itemView);
            case R.layout.item_radio_group:
                return new FilterRadioGroupViewHolder(itemView);
            case R.layout.item_filter_toggle:
                return new FilterToggleItemViewHolder(itemView);
            case R.layout.item_button:
            case R.layout.item_favourite_button:
                return new FilterButtonItemViewHolder(itemView);
            default:
                return null;
        }
    }
    FilterCheckboxViewHolder view;

    @Override
    public void onBindViewHolder(BaseFormViewHolder holder, int position) {
        holder.onBindViewHolder(filterItems.get(position));

        addCheckboxAction(holder);

    }

    private void addCheckboxAction(BaseFormViewHolder holder) {
        if(holder instanceof FilterCheckboxViewHolder){
            view = (FilterCheckboxViewHolder) holder;

            view.setOnCheckedChangeListener((title, isChecked) -> {
                if(title.equalsIgnoreCase("All")) checkedAll();
                else checkedIndividual(title, isChecked);
                notifyDataSetChanged();
            });
        }
    }

    private void checkedAll() {
        for(BaseFormModel modelItem: filterItems){
            if(modelItem instanceof CheckboxTitle){
                CheckboxTitle item = (CheckboxTitle) modelItem;
                boolean isAll = item.getTitle().equalsIgnoreCase("All");
                if(!isAll) item.setValue(false);
                else item.setValue(true);
            }
        }
    }

    private void checkedIndividual(String title, boolean isChecked) {
        for(BaseFormModel modelItem: filterItems)
            if(modelItem instanceof CheckboxTitle){
                CheckboxTitle item = (CheckboxTitle) modelItem;
                boolean isAll = item.getTitle().equalsIgnoreCase("All");
                if(isAll) item.setValue(false);
                else if(item.getTitle().equalsIgnoreCase(title)) item.setValue(isChecked);
            }
    }
}
