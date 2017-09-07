package com.soho.sohoapp.feature.marketplaceview.feature.detailview;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.SohoApplication;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.PropertyDetailDescriptionViewHolder;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.PropertyDetailHeaderViewHolder;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.PropertyLocationImageViewHolder;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.filterviewholder.HeaderViewHolder;

import java.util.List;

import static android.content.pm.PackageManager.GET_META_DATA;

/**
 * Created by chowii on 1/9/17.
 */

class MarketPlaceDetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<BaseModel> modelList;
    private final PropertyDetailAuctionViewHolder.OnAddToCalenderClickListener mListener;

    MarketPlaceDetailAdapter(List<BaseModel> modelList, PropertyDetailAuctionViewHolder.OnAddToCalenderClickListener listener) {
        this.modelList = modelList;
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return modelList.get(position).getItemViewType();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        switch (viewType){
            case R.layout.item_header:
            case R.layout.item_filter_header:
                return new HeaderViewHolder(itemView);
            case R.layout.item_description:
                return new PropertyDetailDescriptionViewHolder(itemView);
            case R.layout.item_property_detail_header:
                return new PropertyDetailHeaderViewHolder(itemView);
            case R.layout.item_property_date_information:
                return new PropertyDetailAuctionViewHolder(itemView, mListener);
            case R.layout.item_property_image:

                String apiKey = "";
                try {
                    Context context = SohoApplication.getContext();
                    ApplicationInfo applicationInfo = SohoApplication.getContext().getPackageManager().getApplicationInfo(context.getPackageName(), GET_META_DATA);
                    apiKey = applicationInfo.metaData.getString("com.google.android.geo.API_KEY", "");
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                return new PropertyLocationImageViewHolder(itemView, apiKey);
            default:
                Log.d("LOG_TAG---", "onCreateViewHolder: ");
                return null;
        }

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindViewHolder(modelList.get(holder.getAdapterPosition()));
    }

}
