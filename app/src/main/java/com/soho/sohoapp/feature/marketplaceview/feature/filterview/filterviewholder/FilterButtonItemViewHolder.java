package com.soho.sohoapp.feature.marketplaceview.feature.filterview.filterviewholder;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.ButtonItem;

import butterknife.BindView;

/**
 * Created by chowii on 23/8/17.
 */

public class FilterButtonItemViewHolder extends BaseFormViewHolder<ButtonItem> {

    @BindView(R.id.button)
    TextView toggleCheckAll;

    private OnSaveFilterPreferenceListener listener;

    public FilterButtonItemViewHolder(View itemView, OnViewHolderItemValueChangeListener listener) {
        super(itemView);
        updatedListener = listener;
    }

    @Override
    public void onBindViewHolder(ButtonItem model) {
        toggleCheckAll.setText(model.getButtonText());
        toggleCheckAll.setOnClickListener(v -> {
            if(listener != null) listener.onSaveClicked(toggleCheckAll.getText().toString());
            updatedListener.onChange(toggleCheckAll.getText().toString(), null);
        });
    }

    public void setOnSaveFilterPreferenceListener(OnSaveFilterPreferenceListener listener) {
        this.listener = listener;
    }

    public interface OnSaveFilterPreferenceListener {
        void onSaveClicked(String title);
    }
}
