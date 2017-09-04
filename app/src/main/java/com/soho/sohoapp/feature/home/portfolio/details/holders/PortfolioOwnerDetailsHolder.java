package com.soho.sohoapp.feature.home.portfolio.details.holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.utils.PropertyCalculator;
import com.soho.sohoapp.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PortfolioOwnerDetailsHolder extends BaseViewHolder<PortfolioProperty> {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.yield)
    TextView yield;
    @BindView(R.id.lvr)
    TextView lvr;
    @BindView(R.id.changedValue)
    TextView changedValue;
    @BindView(R.id.estimatedValue)
    TextView estimatedValue;
    @BindView(R.id.state)
    TextView state;

    private final Context context;
    private OnItemClickListener listener;

    public PortfolioOwnerDetailsHolder(@NonNull Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindViewHolder(PortfolioProperty model) {
        itemView.setOnClickListener(v -> listener.onClick());
        state.setText(model.getState());

        PropertyAddress propertyAddress = model.getPropertyAddress();
        if (propertyAddress != null) {
            title.setText(propertyAddress.getAddressLine1());
        }

        double estimatedValue = model.getPortfolioFinance().getEstimatedValue();
        this.estimatedValue.setText(StringUtils.formatPrice(context, estimatedValue));

        double yield = PropertyCalculator.calculateYield(model);
        this.yield.setText(StringUtils.formatYield(context, yield));


        double lvr = PropertyCalculator.calculateLvr(model);
        this.lvr.setText(StringUtils.formatLvr(context, lvr));

        double changedValue = PropertyCalculator.calculateValueChange(model);
        this.changedValue.setText(StringUtils.formatChangedValue(context, changedValue));
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick();
    }
}
