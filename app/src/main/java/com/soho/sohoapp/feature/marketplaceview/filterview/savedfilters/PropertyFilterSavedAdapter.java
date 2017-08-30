package com.soho.sohoapp.feature.marketplaceview.filterview.savedfilters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.HeaderViewHolder;
import com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder.SavedItemViewHolder;
import com.soho.sohoapp.home.BaseModel;

import java.util.List;

/**
 * Created by chowii on 25/8/17.
 */

class PropertyFilterSavedAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<BaseModel> jsonObject;

    public PropertyFilterSavedAdapter(List<BaseModel> jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public int getItemCount() {
        return jsonObject.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) return R.layout.item_filter_header;
        else return R.layout.item_filter_saved;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        switch(viewType){
            case R.layout.item_filter_header:
                return new HeaderViewHolder(itemView);
            case R.layout.item_filter_saved:
                return new SavedItemViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindViewHolder(jsonObject.get(holder.getAdapterPosition()));
//        if(holder instanceof SavedItemViewHolder){
//            SavedItemViewHolder item = (SavedItemViewHolder) holder;
//            item.titleTextView.setText("Filter " + position);
//            item.subtitleTextView.setText(jsonObject.get(position-1));
//        }
    }

}
