package com.soho.sohoapp.feature.marketplace;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.home.BaseModel;

import java.util.List;

/**
 * Created by chowii on 14/8/17.
 */

class MarketPlaceAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<? extends BaseModel> propertyList;

    MarketPlaceAdapter(List<? extends BaseModel> propertyList) {
        this.propertyList = propertyList;
    }

    @Override
    public int getItemCount() { return propertyList.size(); }

    @Override
    public int getItemViewType(int position) {
        return propertyList.get(position).getItemViewType();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext())
                .inflate(viewType,
                        parent,
                        false);
        return new PropertyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindViewHolder(propertyList.get(holder.getAdapterPosition()));
    }

}
