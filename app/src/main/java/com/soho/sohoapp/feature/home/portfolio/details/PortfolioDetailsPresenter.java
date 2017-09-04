package com.soho.sohoapp.feature.home.portfolio.details;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.feature.common.SohoButton;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.navigator.Navigator;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.network.ApiClient;
import com.soho.sohoapp.network.results.PortfolioPropertyResult;
import com.soho.sohoapp.utils.Converter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PortfolioDetailsPresenter implements AbsPresenter, PortfolioDetailsContract.ViewActionsListener {
    private final PortfolioDetailsContract.View view;
    private final Navigator navigator;
    private final CompositeDisposable compositeDisposable;
    private PortfolioCategory portfolio;

    public PortfolioDetailsPresenter(PortfolioDetailsContract.View view, Navigator navigator) {
        this.view = view;
        this.navigator = navigator;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
        if (view.isOwnerScreen()) {
            portfolio = view.getOwnerPortfolio();
        } else {
            portfolio = view.getManagerPortfolio();
        }
        loadData();
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }

    @Override
    public void onAddPropertyClicked() {
        navigator.openAddPropertyScreen(RequestCode.PORTFOLIO_ADD_PROPERTY_REQUEST_CODE);
    }

    @Override
    public void onPullToRefresh() {
        loadData();
    }

    @Override
    public void onNewPropertyCreated() {
        loadData();
    }

    @Override
    public void onOwnerPropertyClicked(PortfolioProperty property) {
        navigator.openEditPropertyScreen(property);
    }

    private void loadData() {
        view.showPullToRefresh();
        Disposable disposable = ApiClient.getService().getPortfolios(portfolio.getFilterForPortfolio(), portfolio.getUserId())
                .map((List<PortfolioPropertyResult> results) -> Converter.toPortfolioPropertyList(results, !view.isOwnerScreen()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(portfolioProperties -> {
                    List<BaseModel> dataList = new ArrayList<>();
                    dataList.addAll(portfolioProperties);
                    dataList.add(new SohoButton("+ Add property"));
                    view.setData(dataList);
                    view.hidePullToRefresh();
                }, throwable -> {
                    throwable.printStackTrace();
                    view.hidePullToRefresh();
                    view.showLoadingError();
                });
        compositeDisposable.add(disposable);
    }
}
