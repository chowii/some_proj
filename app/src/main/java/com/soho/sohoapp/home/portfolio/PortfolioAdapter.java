package com.soho.sohoapp.home.portfolio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseModel;
import com.soho.sohoapp.home.portfolio.holders.ButtonHolder;
import com.soho.sohoapp.home.portfolio.holders.PortfolioManagerHolder;
import com.soho.sohoapp.home.portfolio.holders.PortfolioOwnerHolder;
import com.soho.sohoapp.home.portfolio.holders.TitleHolder;

import java.util.ArrayList;
import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<BaseModel> dataList;
    private Context context;
    private Listener listener;

    public PortfolioAdapter(@NonNull Context context) {
        this.context = context;
        dataList = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.item_title:
                return new TitleHolder(itemView);
            case R.layout.item_button:
                return new ButtonHolder(itemView, () -> listener.onAddPropertyClicked());
            case R.layout.item_owner_portfolio:
                return new PortfolioOwnerHolder(context, itemView);
            case R.layout.item_manager_portfolio:
                return new PortfolioManagerHolder(context, itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindViewHolder(dataList.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getItemViewType();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setData(@NonNull List<BaseModel> newData) {
        if (dataList != null) {
            dataList = newData;
        }
        notifyDataSetChanged();
    }

    public interface Listener {
        void onAddPropertyClicked();
    }
}