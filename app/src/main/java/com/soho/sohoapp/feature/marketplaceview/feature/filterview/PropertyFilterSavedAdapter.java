package com.soho.sohoapp.feature.marketplaceview.feature.filterview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseModel;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chowii on 25/8/17.
 */

class PropertyFilterSavedAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final List<String> jsonObject;

    public PropertyFilterSavedAdapter(List<String> jsonObject) { this.jsonObject = jsonObject; }

    @Override
    public int getItemCount() { return jsonObject.size() + 1; }

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
                return new FilterSavedHeaderViewHolder(itemView);
            case R.layout.item_filter_saved:
                return new SavedItemViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if(holder instanceof SavedItemViewHolder){
            SavedItemViewHolder item = (SavedItemViewHolder) holder;
            item.titleTextView.setText("Filter " + position);
            item.subtitleTextView.setText(jsonObject.get(position-1));
        }
    }

    class SavedItemViewHolder extends BaseViewHolder{

        @BindView(R.id.title_text_view)
        TextView titleTextView;

        @BindView(R.id.subtitle_text_view)
        TextView subtitleTextView;

        SavedItemViewHolder(View itemView) { super(itemView); }

        @Override
        public void onBindViewHolder(BaseModel model) { }
    }
}
