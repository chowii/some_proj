package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.ButtonItem;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;

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

    public FilterButtonItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
    }

    @Override
    public void onBindViewHolder(ButtonItem model) {
        toggleCheckAll.setText(model.getButtonText());
        toggleCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleCheckAll.getText().toString().equalsIgnoreCase("Save this search")){
                    Log.d("LOG_TAG---", "onClick: " + toggleCheckAll.getText());
                    if(listener != null) listener.onSaveClicked();
                }
            }
        });
    }

    public void setOnSaveFilterPreferenceListener(OnSaveFilterPreferenceListener listener) {
        this.listener = listener;
    }

   public interface OnSaveFilterPreferenceListener {
        void onSaveClicked();
    }

    private void toJson() {



    }
}
