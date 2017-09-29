package com.soho.sohoapp;

import android.view.View;

import com.soho.sohoapp.feature.home.BaseFormModel;
import com.soho.sohoapp.feature.marketplaceview.feature.filters.filterviewholder.OnViewHolderItemValueChangeListener;

import butterknife.ButterKnife;

/**
 * Created by chowii on 22/8/17.
 */

public abstract class BaseFormViewHolder<T extends BaseFormModel> extends BaseViewHolder<T> {

    protected OnViewHolderItemValueChangeListener updatedListener;

    protected BaseFormViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
