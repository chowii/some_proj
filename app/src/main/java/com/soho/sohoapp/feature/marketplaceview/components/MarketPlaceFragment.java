package com.soho.sohoapp.feature.marketplaceview.components;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.enums.MarketplaceFilterSaleType;
import com.soho.sohoapp.data.models.BasicProperty;
import com.soho.sohoapp.data.models.PaginationInformation;
import com.soho.sohoapp.database.entities.MarketplaceFilter;
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs;
import com.soho.sohoapp.feature.marketplaceview.components.MarketPlaceContract.ViewPresentable;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.PropertyDetailActivity;
import com.soho.sohoapp.feature.marketplaceview.feature.filters.PropertyFilterActivity;
import com.soho.sohoapp.landing.BaseFragment;
import com.soho.sohoapp.utils.PaginatedAdapterListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.soho.sohoapp.navigator.RequestCode.FILTER_ACTIVITY_REQUEST_CODE;

/**
 * Created by chowii on 14/8/17.
 */

public class MarketPlaceFragment extends BaseFragment implements
        TabLayout.OnTabSelectedListener,
        MarketPlaceContract.ViewInteractable,
        PaginatedAdapterListener<BasicProperty> {

    public static final String TAG = "MarketPlaceFragment";
    private @MarketplaceFilterSaleType
    String saleType = MarketplaceFilterSaleType.SALE;

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

    @BindView(R.id.order_selector_btn)
    TextView orderByTextView;

    @OnClick(R.id.ll_search_bar)
    public void onSearchTextClicked(View view) {
        Intent filterIntent = new Intent(getActivity(), PropertyFilterActivity.class);
        startActivityForResult(filterIntent, FILTER_ACTIVITY_REQUEST_CODE);
    }

    @OnClick(R.id.order_selector_btn)
    public void onClickOrderSelector() {
        presenter.showOrderDialog(saleType);
    }

    private MarketPlacePresenter presenter;
    private ViewPresentable presentable;
    private MarketPlaceAdapter adapter;

    // MARK: - ================== LifeCycle related ==================

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);
        ButterKnife.bind(this, view);
        configureSwipeLayout();
        presenter = new MarketPlacePresenter(this, this.getContext());
        presenter.createPresentation();
        presenter.startPresenting();
        return view;
    }

    @Override
    public void onDestroyView() {
        presentable.stopPresenting();
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILTER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            presentable.retrieveFiltersAndPerformFullRefresh();
        }
    }

    // MARK: - ================== General methods ==================

    private void configureSwipeLayout() {
        swipeLayout.setOnRefreshListener(() -> presenter.performFullRefresh());
        int swipeProgressViewOffset = ((int) getResources().getDimension(R.dimen.marketplace_search_height));
        swipeLayout.setProgressViewOffset(false, swipeProgressViewOffset, swipeProgressViewOffset + 72);
    }

    private void initAdapterIfNeeded() {
        if (adapter == null) {
            adapter = new MarketPlaceAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.getRecycledViewPool().setMaxRecycledViews(R.id.image_view_pager, 1);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    // MARK: - ================== MarketPlaceContract.ViewInteractable methods ==================

    @Override
    public void configureTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.marketplace_buy_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.marketplace_rent_tab));
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void setPresentable(ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        saleType = getString(R.string.marketplace_buy_tab)
                .equalsIgnoreCase(tab.getText().toString()) ? MarketplaceFilterSaleType.SALE : MarketplaceFilterSaleType.RENT;
        presenter.saleTypeChanged(saleType);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    @Override
    public void showProgressBar() {
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showError(Throwable error) {
        handleError(error);
    }

    @Override
    public void fullRefreshComplete(List<BasicProperty> properties, @Nullable PaginationInformation paginationInformation) {
        initAdapterIfNeeded();
        adapter.replaceDataset(properties, paginationInformation);
    }

    @Override
    public void nextPageLoaded(List<BasicProperty> properties, @Nullable PaginationInformation paginationInformation) {
        initAdapterIfNeeded();
        adapter.addAdditionalItems(properties, paginationInformation);
    }

    @Override
    public void configureViewForFilter(MarketplaceFilterWithSuburbs currentFilter) {
        MarketplaceFilter marketplaceFilter = currentFilter.getMarketplaceFilter();
        tabLayout.getTabAt(marketplaceFilter.isSaleFilter() ? 0 : 1).select();
        priceRangeTextView.setText(
                marketplaceFilter.priceRangeDisplayString(getString(R.string.filters_search_bar_display_format),
                        getString(R.string.dollar_format),
                        getString(R.string.filters_price_any))
        );
        suburbsTextView.setText(currentFilter.suburbsDisplayString(
                getString(R.string.filters_multiple_suburbs_format),
                getString(R.string.filters_all_suburbs))
        );
//        sort by label
        String orderByText = getString(R.string.market_order_by_text_view_text);
        @SuppressWarnings("ConstantConditions")
        String orderByHumanReadable = getString(OrderPropertyDialog.getSealedClass(marketplaceFilter.getOrderByType()
                , marketplaceFilter.getOrderDirection()).getStringRes());

        String orderType = orderByText + orderByHumanReadable;
        Spannable spannable = new SpannableString(orderType);
        spannable.setSpan(new ForegroundColorSpan(Color.GREEN), orderByText.length()
                , (orderByText + orderByHumanReadable).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        orderByTextView.setText(spannable, TextView.BufferType.SPANNABLE);
    }

    // MARK: - ================== PaginatedAdapterListener<BasicProperty> ==================

    @Override
    public void adapterItemClicked(@NonNull BasicProperty item) {
        Intent detailIntent = PropertyDetailActivity.createIntent(getActivity(), item.getId());
        getActivity().startActivity(detailIntent);
    }

    @Override
    public void shouldLoadNextPage(int page) {
        presentable.fetchNextPage(page);
    }
}
