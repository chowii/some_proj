package com.soho.sohoapp.home.addproperty.investment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.R;
import com.soho.sohoapp.landing.BaseFragment;

import butterknife.ButterKnife;

public class InvestmentFragment extends BaseFragment implements InvestmentContract.View {
    private InvestmentContract.ViewActionsListener actionsListener;

    @NonNull
    public static Fragment newInstance() {
        return new InvestmentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_investment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setActionsListener(InvestmentContract.ViewActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }
}
