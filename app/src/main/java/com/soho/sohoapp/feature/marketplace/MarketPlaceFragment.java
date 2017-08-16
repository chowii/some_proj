package com.soho.sohoapp.feature.marketplace;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseModel;
import com.soho.sohoapp.landing.BaseFragment;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 14/8/17.
 */

public class MarketPlaceFragment extends BaseFragment implements
        TabLayout.OnTabSelectedListener,
        MarketPlaceContract.ViewInteractable
{

    public static MarketPlaceFragment newInstance() {
        MarketPlaceFragment fragment = new MarketPlaceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    MarketPlacePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);
        ButterKnife.bind(this, view);

        presenter = new MarketPlacePresenter(this);
        presenter.startPresenting();
        swipeLayout.setOnRefreshListener(() -> presenter.onRefresh());
        return view;
    }

    @Override
    public void configureTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.marketplace_buy_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.marketplace_rent_tab));
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.getTabAt(0).select();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getText().toString().equalsIgnoreCase(getString(R.string.marketplace_buy_tab)))
            presenter.startPresenting();
        else presenter.startPresenting();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    @Override
    public void showRefreshing() {
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshing() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void configureAdapter(List<? extends BaseModel> model) {
        initAdapter(model);
    }

    void initAdapter(List<? extends BaseModel> propertyList){
        recyclerView.setAdapter(new MarketPlaceAdapter(propertyList));
        recyclerView.getRecycledViewPool().setMaxRecycledViews(R.id.image_view_pager, 1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onDestroyView() {
        presenter.stopPresenting();
        presenter = null;
        super.onDestroyView();
    }

}
