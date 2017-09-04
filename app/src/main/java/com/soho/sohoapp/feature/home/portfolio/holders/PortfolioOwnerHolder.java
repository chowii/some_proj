package com.soho.sohoapp.feature.home.portfolio.holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PortfolioOwnerHolder extends BaseViewHolder<PortfolioCategory> {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.properties)
    TextView properties;
    @BindView(R.id.estimatedValue)
    TextView estimatedValue;

    private Context context;
    private Listener listener;

    @Override
    public void onBindViewHolder(PortfolioCategory model) {
        itemView.setOnClickListener(v -> listener.onClick());
        name.setText(model.getName());

        int propertyCount = model.getPropertyCount();
        properties.setText(context.getResources()
                .getQuantityString(R.plurals.portfolio_properties_quantity, propertyCount, propertyCount));

        estimatedValue.setText(StringUtils.formatPrice(context, model.getEstimatedValue()));
    }

    public PortfolioOwnerHolder(@NonNull Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onClick();
    }
}
