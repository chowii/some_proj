package com.soho.sohoapp.home.addproperty.type;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.PropertyType;

import java.util.ArrayList;
import java.util.List;

public class PropertyTypeAdapter extends RecyclerView.Adapter<PropertyTypeAdapter.ViewHolder> {
    private List<PropertyType> propertyTypeList;
    private Listener listener;

    public PropertyTypeAdapter() {
        propertyTypeList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_type_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PropertyType propertyType = propertyTypeList.get(position);
        holder.type.setText(propertyType.getLabel());
        holder.type.setOnClickListener(v -> listener.onPropertyTypeClicked(propertyType));
    }

    @Override
    public int getItemCount() {
        return propertyTypeList.size();
    }

    public void setListener(@NonNull Listener listener) {
        this.listener = listener;
    }

    public void setData(@NonNull List<PropertyType> newPropertyTypeList) {
        if (propertyTypeList != null) {
            propertyTypeList = newPropertyTypeList;
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button type;

        public ViewHolder(View itemView) {
            super(itemView);
            type = (Button) itemView.findViewById(R.id.type);
        }
    }

    public interface Listener {
        void onPropertyTypeClicked(PropertyType propertyType);
    }
}
