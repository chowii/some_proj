package com.soho.sohoapp.feature.home.portfolio.holders;

import android.view.View;
import android.widget.Button;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.common.SohoButton;

import butterknife.BindView;

public class ButtonHolder extends BaseViewHolder<SohoButton> {
    @BindView(R.id.button)
    Button button;
    private Listener listener;

    public ButtonHolder(View itemView, Listener listener) {
        super(itemView);
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(SohoButton model) {
        button.setText(model.getText());
        button.setOnClickListener(v -> listener.onClicked());
    }

    public interface Listener {
        void onClicked();
    }
}
