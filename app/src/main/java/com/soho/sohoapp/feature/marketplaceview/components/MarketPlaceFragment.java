package com.soho.sohoapp.feature.marketplaceview.components;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.PropertyDetailActivity;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.PropertyFilterActivity;
import com.soho.sohoapp.landing.BaseFragment;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chowii on 14/8/17.
 */

public class MarketPlaceFragment extends BaseFragment implements
        TabLayout.OnTabSelectedListener,
        MarketPlaceContract.ViewInteractable,
        PropertyViewHolder.OnMarketplaceItemClickListener
{

    public static final String TAG = "MarketPlaceFragment";
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

    @BindView(R.id.search_text)
    TextView tv;

    @OnClick(R.id.search_text)
    public void onSearchTextClicked(View view){
        Intent filterIntent = new Intent(getActivity(), PropertyFilterActivity.class);

        filterIntent.putExtra("is_buy_section", searchParams.get("by_listing_type") == "sale/auction");
        startActivity(filterIntent);
    }

    MarketPlacePresenter presenter;
    Map<String, Object> searchParams;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);
        ButterKnife.bind(this, view);

        searchParams = (Map<String, Object>) getActivity().getIntent().getSerializableExtra("searchParams");
        if(searchParams == null) searchParams = new HashMap<>();
        searchParams.put("by_listing_type", "sale/auction");

        presenter = new MarketPlacePresenter(this);
        presenter.createPresentation();
        presenter.startPresenting(searchParams);
        swipeLayout.setOnRefreshListener(() -> presenter.onRefresh(searchParams));
        return view;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void configureTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.marketplace_buy_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.marketplace_rent_tab));
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.getTabAt(0).select();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(getString(R.string.marketplace_buy_tab).equalsIgnoreCase(tab.getText().toString())){
            searchParams.put("by_listing_type", "sale/auction");
            presenter.startPresenting(searchParams);
        }else{
            searchParams.put("by_listing_type", "rent");
            presenter.startPresenting(searchParams);
        }
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
        recyclerView.setAdapter(new MarketPlaceAdapter(propertyList, this));
        recyclerView.getRecycledViewPool().setMaxRecycledViews(R.id.image_view_pager, 1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onDestroyView() {
        presenter.stopPresenting();
        presenter.destroyPresentation();
        presenter = null;
        super.onDestroyView();
    }

    @Override
    public void onMarketplaceItemClicked(int id) {
        Intent detailIntent = new Intent(getActivity(), PropertyDetailActivity.class);
        detailIntent.putExtra("property_id", id);
        getActivity().startActivity(detailIntent);
    }
}
