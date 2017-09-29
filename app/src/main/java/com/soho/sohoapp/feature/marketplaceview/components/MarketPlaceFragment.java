package com.soho.sohoapp.feature.marketplaceview.components;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.PropertyDetailActivity;
import com.soho.sohoapp.feature.marketplaceview.feature.filters.PropertyFilterActivity;
import com.soho.sohoapp.landing.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by chowii on 14/8/17.
 */

public class MarketPlaceFragment extends BaseFragment implements
        TabLayout.OnTabSelectedListener,
        MarketPlaceContract.ViewInteractable,
        PropertyViewHolder.OnMarketplaceItemClickListener
{

    private static final int FILTER_ACTIVITY_REQUEST_CODE = 18;
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

    @BindView(R.id.text_suburbs)
    TextView suburbsTextView;

    @BindView(R.id.text_price_range)
    TextView priceRangeTextView;

    @OnClick(R.id.ll_search_bar)
    public void onSearchTextClicked(View view){
        Intent filterIntent = new Intent(getActivity(), PropertyFilterActivity.class);
        startActivityForResult(filterIntent, FILTER_ACTIVITY_REQUEST_CODE);
    }

    MarketPlacePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);
        ButterKnife.bind(this, view);
        configueSwipeLayout();
        presenter = new MarketPlacePresenter(this);
        presenter.createPresentation();
        presenter.startPresenting();
        return view;
    }

    private void configueSwipeLayout() {
        swipeLayout.setOnRefreshListener(() -> presenter.onRefresh());
        int swipeProgressViewOffset = ((int) getResources().getDimension(R.dimen.marketplace_search_height));
        swipeLayout.setProgressViewOffset(false, swipeProgressViewOffset, swipeProgressViewOffset + 72);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void configureTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.marketplace_buy_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.marketplace_rent_tab));
        tabLayout.addOnTabSelectedListener(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        presenter.saleTypeChanged(getString(R.string.marketplace_buy_tab).equalsIgnoreCase(tab.getText().toString()) ? "sale/auction" : "rent");
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
    public void showError(Throwable error) {
        handleError(error);
    }

    @Override
    public void configureAdapter(List<? extends BaseModel> model) {
        initAdapter(model);
    }

    @Override
    public void configureViewForFilter(MarketplaceFilterWithSuburbs currentFilter) {
        tabLayout.getTabAt(currentFilter.getMarketplaceFilter().isSaleFilter() ? 0 : 1).select();
        priceRangeTextView.setText(
                currentFilter.getMarketplaceFilter().priceRangeDisplayString(getString(R.string.filters_search_bar_display_format),
                getString(R.string.dollar_format),
                getString(R.string.filters_price_any))
        );
        suburbsTextView.setText(currentFilter.suburbsDisplayString(
                getString(R.string.filters_multiple_suburbs_format),
                getString(R.string.filters_all_suburbs))
        );
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
        Intent detailIntent = PropertyDetailActivity.createIntent(getActivity(), id);
        getActivity().startActivity(detailIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FILTER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            presenter.startPresenting();
        }
    }
}
