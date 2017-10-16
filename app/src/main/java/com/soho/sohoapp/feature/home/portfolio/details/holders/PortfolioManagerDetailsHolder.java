package com.soho.sohoapp.feature.home.portfolio.details.holders;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PortfolioManagerDetailsHolder extends BaseViewHolder<PortfolioProperty> {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.state)
    TextView state;

    private PortfolioOwnerDetailsHolder.OnItemClickListener listener;

    public PortfolioManagerDetailsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(PortfolioProperty model) {
        itemView.setOnClickListener(v -> listener.onClick());
        title.setText(model.getLocation().getAddressLine1());
        state.setText(model.getState());
    }

    public void setListener(PortfolioOwnerDetailsHolder.OnItemClickListener listener) {
        this.listener = listener;
    }
}
