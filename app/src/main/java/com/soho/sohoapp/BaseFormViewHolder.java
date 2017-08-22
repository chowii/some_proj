package com.soho.sohoapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soho.sohoapp.home.BaseFormModel;

/**
 * Created by chowii on 22/8/17.
 */

public abstract class BaseFormViewHolder<T extends BaseFormModel> extends RecyclerView.ViewHolder {
    public BaseFormViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(T model);
}
