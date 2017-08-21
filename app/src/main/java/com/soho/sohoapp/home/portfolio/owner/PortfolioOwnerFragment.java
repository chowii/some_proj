package com.soho.sohoapp.home.portfolio.owner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.R;

import butterknife.ButterKnife;

public class PortfolioOwnerFragment extends Fragment {
    @NonNull
    public static Fragment newInstance() {
        return new PortfolioOwnerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio_owner, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
