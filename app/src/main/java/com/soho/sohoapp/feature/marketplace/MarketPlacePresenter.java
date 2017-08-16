package com.soho.sohoapp.feature.marketplace;

import com.soho.sohoapp.network.ApiClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chowii on 15/8/17.
 */

public class MarketPlacePresenter implements
        MarketPlaceContract.ViewPresentable {

    private MarketPlaceContract.ViewInteractable interactable;

    MarketPlacePresenter(MarketPlaceContract.ViewInteractable intractable){
        this.interactable = intractable;
    }

    @Override
    public void startPresenting() {
        interactable.configureTabLayout();
        loadData();
    }

    private void loadData() {
        interactable.showRefreshing();
        ApiClient.getService().searchProperties()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        sohoProperties -> {
                            interactable.configureAdapter(sohoProperties);
                            interactable.hideRefreshing();
                        }, throwable -> {
                            interactable.hideRefreshing();
                        }
                );
    }

    @Override
    public void stopPresenting() {
        this.interactable = null;
    }

    @Override
    public void onRefresh(){
        loadData();
    }
}
