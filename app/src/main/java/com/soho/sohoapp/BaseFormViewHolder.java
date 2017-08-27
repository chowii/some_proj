package com.soho.sohoapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soho.sohoapp.home.BaseFormModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by chowii on 22/8/17.
 */

public abstract class BaseFormViewHolder<T extends BaseFormModel> extends RecyclerView.ViewHolder {

    Map<String, Object> itemMap = new HashMap<>();

    public BaseFormViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void onBindViewHolder(T model);
}
