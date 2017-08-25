package com.soho.sohoapp.home.portfolio.holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.home.portfolio.data.PortfolioManagerCategory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PortfolioManagerHolder extends BaseViewHolder<PortfolioManagerCategory> {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.properties)
    TextView properties;
    @BindView(R.id.publicCount)
    TextView publicCount;

    private final Context context;
    private Listener listener;

    public PortfolioManagerHolder(@NonNull Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(PortfolioManagerCategory model) {
        itemView.setOnClickListener(v -> listener.onClick());
        name.setText(model.getName());

        int propertyCount = model.getPropertyCount();
        properties.setText(context.getResources()
                .getQuantityString(R.plurals.portfolio_properties_quantity, propertyCount, propertyCount));
        publicCount.setText(context.getString(R.string.portfolio_public_count, model.getPublicPropertiesCount()));
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onClick();
    }
}