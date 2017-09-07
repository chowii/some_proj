package com.soho.sohoapp.feature.marketplaceview.components;

import android.util.Log;

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
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(
                                sohoProperties -> {
                                    interactable.configureAdapter(sohoProperties);
                                    interactable.hideRefreshing();
                                }, throwable -> {
                                    interactable.hideRefreshing();
                                    Log.e("LOG_TAG---","throwable" + throwable.getMessage());
                                    Log.d("LOG_TAG---", ": ");
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
