package com.soho.sohoapp.home.portfolio.details;

import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.navigator.Navigator;
import com.soho.sohoapp.network.ApiClient;
import com.soho.sohoapp.network.results.PortfolioPropertyResult;
import com.soho.sohoapp.utils.Converter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
        Disposable disposable = ApiClient.getService().getPortfolios(portfolio.getFilterForPortfolio(), portfolio.getUserId())
                .map((List<PortfolioPropertyResult> results) -> Converter.toPortfolioPropertyList(results, !view.isOwnerScreen()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PortfolioProperty>>() {
                    @Override
                    public void accept(List<PortfolioProperty> portfolioProperties) throws Exception {
                        System.out.println("Portfolio list " + portfolioProperties.size());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    @Override
    public void onBackClicked() {
        navigator.exitCurrentScreen();
    }
}
