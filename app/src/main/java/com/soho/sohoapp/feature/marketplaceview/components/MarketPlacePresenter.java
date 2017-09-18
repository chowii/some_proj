package com.soho.sohoapp.feature.marketplaceview.components;

import com.soho.sohoapp.utils.Converter;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

/**
 * Created by chowii on 15/8/17.
 */

class MarketPlacePresenter implements
        MarketPlaceContract.ViewPresentable {

    private final CompositeDisposable compositeDisposable;
    private MarketPlaceContract.ViewInteractable interactable;

    MarketPlacePresenter(MarketPlaceContract.ViewInteractable intractable){
        this.interactable = intractable;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void createPresentation() {
        interactable.configureTabLayout();
    }

    @Override
    public void destroyPresentation() {
        interactable = null;
    }

    @Override
    public void startPresenting(Map<String, Object> searchParams) {
        loadData(searchParams);
    }

    private void loadData(Map<String, Object> searchParams) {
        interactable.showRefreshing();
        compositeDisposable.add(
                DEPENDENCIES.getSohoService().searchProperties(searchParams)
                        .map(Converter::toBasicProperties)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(properties -> {
                                    interactable.configureAdapter(properties);
                                    interactable.hideRefreshing();
                                }, throwable -> {
                                    interactable.hideRefreshing();
                                    throwable.printStackTrace();
                                }
                        )
        );
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    @Override
    public void onRefresh(Map<String, Object> searchParams){ loadData(searchParams);
         }
}
