package com.soho.sohoapp.feature.marketplaceview.filterview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.BaseFormViewHolder;
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

import java.util.List;

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
    FilterCheckboxViewHolder view;

    @Override
    public void onBindViewHolder(BaseFormViewHolder holder, int position) {
        holder.onBindViewHolder(filterItems.get(position));

        if(holder instanceof FilterCheckboxViewHolder){
            view = (FilterCheckboxViewHolder) holder;

            String checkBoxText = view.titleTextBox.getText().toString();
            view.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(BaseFormModel formModel: filterItems)
                        toggleCheckbox(formModel, checkBoxText.equalsIgnoreCase("All"));

                    notifyDataSetChanged();
                }

                private void toggleCheckbox(BaseFormModel formModel, boolean isCheckboxAll) {
                    if(formModel instanceof CheckboxTitle){
                        CheckboxTitle model = (CheckboxTitle) formModel;
                        if(isCheckboxAll)
                            toggleCheckboxAll(model, model.getTitle().equalsIgnoreCase("all"), isCheckboxAll);
                        else toggleCheckboxAll(model, model.getTitle().equalsIgnoreCase("all"), isCheckboxAll);
                    }
                }

                /**
                 * Toggles when the checkbox with text "All" is selected, all the other checkboxes are
                 * checked off and checks itself off when the checkbox is checked on.
                 * Toggles off checkbox with text "All" when a checkbox is clicked, and checks itself off
                 * when checked on.
                 *
                 * @param model
                 * @param isToggleWhenCheckIsAll true if the clicked item is the same item on the list of items
                 * @param isCheckAll true if the clicked item is the checkbox with text "All"
                 */
                private void toggleCheckboxAll(CheckboxTitle model, boolean isToggleWhenCheckIsAll, boolean isCheckAll){
                    if(isCheckAll)
                        if(isToggleWhenCheckIsAll) model.setValue(view.checkBox.isChecked());
                        else model.setValue(isToggleWhenCheckIsAll);
                    else if(!isToggleWhenCheckIsAll){
                        if(model.getTitle().equalsIgnoreCase(view.titleTextBox.getText().toString()))
                            model.setValue(view.checkBox.isChecked());
                    }else model.setValue(false);
                }

            });
        }

    }


}
