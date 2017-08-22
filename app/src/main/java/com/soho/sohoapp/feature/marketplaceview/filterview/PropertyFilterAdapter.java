package com.soho.sohoapp.feature.marketplaceview.filterview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.FilterCheckboxViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.FilterRadioGroupViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.FilterRangeViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.FilterValueSelectorViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.HeaderViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.RecyclerViewViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.TextSearchViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.CheckboxTitle;
import com.soho.sohoapp.home.BaseFormModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by chowii on 18/8/17.
 */

class PropertyFilterAdapter extends RecyclerView.Adapter<BaseFormViewHolder> {

    List<BaseFormModel> filterItems;

    PropertyFilterAdapter(List<BaseFormModel> filterItems) {
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
            default:
                return null;
        }
    }
    List<FilterCheckboxViewHolder> checkboxViewHolderList;

    @Override
    public void onBindViewHolder(BaseFormViewHolder holder, int position) {
        holder.onBindViewHolder(filterItems.get(position));

        if(holder instanceof FilterCheckboxViewHolder){
            FilterCheckboxViewHolder view = (FilterCheckboxViewHolder) holder;
            checkboxViewHolderList = new ArrayList<>();

            checkboxViewHolderList.add(view);

            String checkBoxText = view.titleTextBox.getText().toString();
            view.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBoxText.equalsIgnoreCase("All")){
                        for(BaseFormModel formModel: filterItems)
                            if(formModel instanceof CheckboxTitle){
                                CheckboxTitle c = (CheckboxTitle) formModel;
                                if(c.getTitle().equalsIgnoreCase("all")){
                                    c.setValue(view.checkBox.isChecked());
                                }else{
                                    if(!c.getTitle().equalsIgnoreCase("All")){
                                        c.setValue(false);
                                    }
                                }
                            }
                        notifyDataSetChanged();
                    }
                }
            });
        }

    }


}
