package com.soho.sohoapp.feature.home.portfolio;

import android.content.res.Resources;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.feature.common.SohoButton;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioCategory;
import com.soho.sohoapp.feature.home.portfolio.data.PortfolioManagerCategory;
import com.soho.sohoapp.feature.home.portfolio.data.Title;
import com.soho.sohoapp.navigator.NavigatorInterface;
import com.soho.sohoapp.navigator.RequestCode;
import com.soho.sohoapp.utils.Converter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class PortfolioManagerPresenter implements AbsPresenter, PortfolioListContract.ViewPresentable {
    private final PortfolioListContract.ViewInteractable view;
    private final NavigatorInterface navigator;
    private final Resources resources;
    private final CompositeDisposable compositeDisposable;

    public PortfolioManagerPresenter(PortfolioListContract.ViewInteractable view, NavigatorInterface navigator, Resources resources) {
        this.view = view;
        this.navigator = navigator;
        this.resources = resources;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
        view.setPresentable(this);
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
        navigator.openAddPropertyScreen(RequestCode.PORTFOLIO_MANAGER_LIST_ADD_PROPERTY_REQUEST_CODE);
    }

    @Override
    public void onPortfolioClicked(PortfolioCategory portfolioCategory) {
        navigator.openManagerPortfolioDetails((PortfolioManagerCategory) portfolioCategory);
    }

    @Override
    public void onNewPropertyCreated() {
        loadPortfolios();
    }

    private void loadPortfolios() {
        view.showPullToRefresh();
        compositeDisposable.add(DEPENDENCIES.getSohoService().getManagedPortfolios()
                .map(Converter::toPortfolioManagerCategoryList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(portfolioCategories -> {
                    List<BaseModel> dataList = new ArrayList<>();
                    dataList.add(new Title(resources.getString(R.string.portfolio_client_portfolios_list_item)));
                    dataList.addAll(portfolioCategories);
                    dataList.add(new SohoButton(resources.getString(R.string.portfolio_plus_property)));
                    view.setData(dataList);
                    view.hidePullToRefresh();
                }, throwable -> {
                    view.showError(throwable);
                    view.hidePullToRefresh();
                }));
    }
}
