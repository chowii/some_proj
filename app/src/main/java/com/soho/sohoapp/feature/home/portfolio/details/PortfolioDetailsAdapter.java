package com.soho.sohoapp.feature.home.portfolio.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.feature.home.portfolio.details.holders.PortfolioManagerDetailsHolder;
import com.soho.sohoapp.feature.home.portfolio.details.holders.PortfolioOwnerDetailsHolder;
import com.soho.sohoapp.feature.home.portfolio.holders.ButtonHolder;

import java.util.ArrayList;
import java.util.List;

public class PortfolioDetailsAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final Context context;
    private final boolean isFromFavouriteCategory;
    private List<BaseModel> dataList;
    private OnItemClickListener onItemClickListener;

    public PortfolioDetailsAdapter(@NonNull Context context, boolean isFromFavouriteCategory) {
        this.context = context;
        this.isFromFavouriteCategory = isFromFavouriteCategory;
        dataList = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.item_button: {
                itemView.setVisibility(isFromFavouriteCategory ? View.GONE : View.VISIBLE);
                return new ButtonHolder(itemView, () -> onItemClickListener.onAddPropertyClicked());
            }
            case R.layout.item_owner_portfolio_details:
                return new PortfolioOwnerDetailsHolder(context, itemView);
            case R.layout.item_manager_portfolio_details:
                return new PortfolioManagerDetailsHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        BaseModel model = dataList.get(holder.getAdapterPosition());
        switch (model.getItemViewType()) {
            case R.layout.item_owner_portfolio_details:
                PortfolioOwnerDetailsHolder ownerHolder = (PortfolioOwnerDetailsHolder) holder;
                ownerHolder.setListener(() -> onItemClickListener.onOwnerPropertyClicked((PortfolioProperty) model));
                break;
        }
        holder.onBindViewHolder(model);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getItemViewType();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(@NonNull List<BaseModel> newData) {
        if (dataList != null) {
            dataList = newData;
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onAddPropertyClicked();

        void onOwnerPropertyClicked(PortfolioProperty property);
    }
}
