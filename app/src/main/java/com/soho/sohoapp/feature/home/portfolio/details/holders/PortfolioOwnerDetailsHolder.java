package com.soho.sohoapp.feature.home.portfolio.details.holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.extensions.IntExtKt;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.utils.PropertyCalculator;
import com.soho.sohoapp.utils.StringUtils;

import butterknife.BindView;

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
    }

    @Override
    public void onBindViewHolder(PortfolioProperty model) {
        itemView.setOnClickListener(v -> listener.onClick());
        state.setText(model.getState());

        Location location = model.getLocation();
        if (location != null) {
            title.setText(location.getAddressLine1());
        }

        double estimatedValue = model.getPropertyFinance().getEstimatedValue();
        this.estimatedValue.setText(IntExtKt.toShortHand((int) estimatedValue));

        double yield = PropertyCalculator.calculateYield(model.getPropertyFinance());
        this.yield.setText(StringUtils.longFormatYield(context, yield));


        double lvr = PropertyCalculator.calculateLvr(model.getPropertyFinance());
        this.lvr.setText(StringUtils.longFormatLvr(context, lvr));

        double changedValue = PropertyCalculator.calculateValueChange(model.getPropertyFinance());
        this.changedValue.setText(StringUtils.formatChangedValue(context, changedValue));
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick();
    }
}
