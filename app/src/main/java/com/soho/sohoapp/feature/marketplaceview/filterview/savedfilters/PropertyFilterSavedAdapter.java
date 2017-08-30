package com.soho.sohoapp.feature.marketplaceview.filterview.savedfilters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.common.EmptyItemViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.HeaderViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.SavedItemViewHolder;
import com.soho.sohoapp.home.BaseModel;

import java.util.List;

/**
 * Created by chowii on 25/8/17.
 */

class PropertyFilterSavedAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<BaseModel> jsonObject;

    PropertyFilterSavedAdapter(List<BaseModel> jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public int getItemCount() { return jsonObject.size(); }

    @Override
    public int getItemViewType(int position) { return jsonObject.get(position).getItemViewType(); }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        switch(viewType){
            case R.layout.item_filter_header:
                return new HeaderViewHolder(itemView);
            case R.layout.item_filter_saved:
                return new SavedItemViewHolder(itemView);
            case R.layout.item_empty_dataset:
                return new EmptyItemViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindViewHolder(jsonObject.get(holder.getAdapterPosition()));
    }

}
