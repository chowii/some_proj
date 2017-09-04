package com.soho.sohoapp.feature.home.portfolio;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.feature.common.SohoButton;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.feature.home.portfolio.data.Title;
import com.soho.sohoapp.navigator.Navigator;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.network.ApiClient;
import com.soho.sohoapp.utils.Converter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class PortfolioOwnerPresenter implements AbsPresenter, PortfolioListContract.ViewActionsListener {
    private final PortfolioListContract.View view;
    private Navigator navigator;
    private final CompositeDisposable compositeDisposable;

    public PortfolioOwnerPresenter(PortfolioListContract.View view, Navigator navigator) {
        this.view = view;
        this.navigator = navigator;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setActionsListener(this);
        loadPortfolios();
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    @Override
    public void onPullToRefresh() {
        loadPortfolios();
    }

    @Override
    public void onAddPropertyClicked() {
        navigator.openAddPropertyScreen(RequestCode.PORTFOLIO_OWNER_LIST_ADD_PROPERTY_REQUEST_CODE);
    }

    @Override
    public void onPortfolioClicked(PortfolioCategory portfolioCategory) {
        if (PortfolioCategory.FILTER_FAVOURITES.equals(portfolioCategory.getFilterForPortfolio())) {
            navigator.openManagerPortfolioDetails(PortfolioManagerCategory.fromPortfolioCategory(portfolioCategory));
        } else {
            navigator.openOwnerPortfolioDetails(portfolioCategory);
        }
    }

    @Override
    public void onNewPropertyCreated() {
        loadPortfolios();
    }

    private void loadPortfolios() {
        view.showPullToRefresh();
        Disposable disposable = ApiClient.getService().getOwnedPortfolios()
                .map(Converter::toPortfolioCategoryList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(portfolioCategories -> {
                    List<BaseModel> dataList = new ArrayList<>();
                    dataList.add(new Title("PORTFOLIOS"));
                    dataList.addAll(portfolioCategories);
                    dataList.add(new SohoButton("+ Add property"));
                    view.setData(dataList);
                    view.hidePullToRefresh();
                }, throwable -> {
                    view.showError(throwable);
                    view.hidePullToRefresh();
                });
        compositeDisposable.add(disposable);
    }
}