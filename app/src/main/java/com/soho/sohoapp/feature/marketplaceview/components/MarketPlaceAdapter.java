package com.soho.sohoapp.feature.marketplaceview.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.feature.home.BaseModel;

import java.util.List;

/**
 * Created by chowii on 14/8/17.
 */

public class MarketPlaceAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<? extends BaseModel> propertyList;
    private final PropertyViewHolder.OnMarketplaceItemClickListener listener;

    public MarketPlaceAdapter(List<? extends BaseModel> propertyList, PropertyViewHolder.OnMarketplaceItemClickListener listener) {
        this.propertyList = propertyList;
        this.listener = listener;
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
        return new PropertyViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindViewHolder(propertyList.get(holder.getAdapterPosition()));
    }

}
