package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.ButtonItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 23/8/17.
 */

public class FilterButtonItemViewHolder extends BaseFormViewHolder<ButtonItem> {

    @BindView(R.id.button)
    TextView toggleCheckAll;

    private final Context context;
    OnSaveFilterPreferenceListener listener;
    private final OnViewHolderItemValueChangeListener updateListener;

    public FilterButtonItemViewHolder(View itemView, OnViewHolderItemValueChangeListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
        updateListener = listener;
    }

    @Override
    public void onBindViewHolder(ButtonItem model) {
        toggleCheckAll.setText(model.getButtonText());
        toggleCheckAll.setOnClickListener(v -> {
            if(toggleCheckAll.getText().toString().equalsIgnoreCase("Save this search")){
                Log.d("LOG_TAG---", "onClick: " + toggleCheckAll.getText());
                if(listener != null) listener.onSaveClicked();
                updateListener.onChange(toggleCheckAll.getText().toString(), null);
            }
        });
    }

    public void setOnSaveFilterPreferenceListener(OnSaveFilterPreferenceListener listener) {
        this.listener = listener;
    }

   public interface OnSaveFilterPreferenceListener {
        void onSaveClicked();
    }
}
