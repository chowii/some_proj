package com.soho.sohoapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soho.sohoapp.home.BaseModel;

/**
 * Created by chowii on 14/8/17.
 */

public abstract class BaseViewHolder<T extends BaseModel> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(T model);
}
