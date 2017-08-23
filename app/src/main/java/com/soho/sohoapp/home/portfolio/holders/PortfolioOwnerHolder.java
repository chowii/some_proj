package com.soho.sohoapp.home.portfolio.holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.home.portfolio.data.PortfolioCategory;

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

    @Override
    public void onBindViewHolder(PortfolioCategory model) {
        name.setText(model.getName());

        int propertyCount = model.getPropertyCount();
        properties.setText(context.getResources()
                .getQuantityString(R.plurals.portfolio_properties_quantity, propertyCount, propertyCount));

        estimatedValue.setText(context.getString(R.string.portfolio_estimated_value,model.getEstimatedValue()));
    }

    public PortfolioOwnerHolder(@NonNull Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }
}
