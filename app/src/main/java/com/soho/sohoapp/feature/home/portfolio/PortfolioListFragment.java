package com.soho.sohoapp.feature.home.portfolio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.landing.BaseFragment;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.navigator.RequestCode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PortfolioListFragment extends BaseFragment implements PortfolioListContract.ViewInteractable {
    private static final String KEY_MODE = "KEY_MODE";

    @BindView(R.id.portfolioList)
    RecyclerView portfolioList;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private PortfolioListContract.ViewPresentable presentable;
    private AbsPresenter presenter;
    private PortfolioListAdapter adapter;

    @NonNull
    public static Fragment newInstance(@NonNull Mode mode) {
        PortfolioListFragment fragment = new PortfolioListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MODE, mode.name());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolios_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (getMode()) {
            case OWNER:
                presenter = new PortfolioOwnerPresenter(this, NavigatorImpl.newInstance(this), getResources());
                break;
            case MANAGER:
                presenter = new PortfolioManagerPresenter(this, NavigatorImpl.newInstance(this), getResources());
                break;
            default:
                throw new IllegalStateException();
        }

        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    public void onDestroyView() {
        presenter.stopPresenting();
        super.onDestroyView();
    }

    @Override
    public void setPresentable(PortfolioListContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public void setData(List<BaseModel> dataList) {
        adapter.setData(dataList);
    }

    @Override
    public void showError(Throwable throwable) {
        handleError(throwable);
    }

    @Override
    public void showPullToRefresh() {
        if (!swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(true);
        }
    }

    @Override
    public void hidePullToRefresh() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCode.PORTFOLIO_OWNER_LIST_ADD_PROPERTY_REQUEST_CODE
                    || requestCode == RequestCode.PORTFOLIO_MANAGER_LIST_ADD_PROPERTY_REQUEST_CODE) {
                presentable.onNewPropertyCreated();
            }
        }
    }

    private void initView() {
        swipeRefresh.setOnRefreshListener(() -> presentable.onPullToRefresh());
        adapter = new PortfolioListAdapter(getContext());

        adapter.setOnItemClickListener(new PortfolioListAdapter.OnItemClickListener() {
            @Override
            public void onAddPropertyClicked() {
                presentable.onAddPropertyClicked();
            }

            @Override
            public void onPortfolioClicked(PortfolioCategory portfolioCategory) {
                presentable.onPortfolioClicked(portfolioCategory);
            }
        });

        portfolioList.setAdapter(adapter);
        portfolioList.setHasFixedSize(true);
        portfolioList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
    }

    private Mode getMode() {
        return Mode.valueOf(getArguments().getString(KEY_MODE));
    }

    public enum Mode {
        OWNER, MANAGER
    }
}
