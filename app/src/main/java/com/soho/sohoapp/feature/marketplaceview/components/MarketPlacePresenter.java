package com.soho.sohoapp.feature.marketplaceview.components;

import com.soho.sohoapp.database.SohoDatabaseKt;
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs;
import com.soho.sohoapp.database.entities.Suburb;
import com.soho.sohoapp.utils.Converter;
import com.soho.sohoapp.utils.QueryHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;
import static com.soho.sohoapp.network.Keys.Filter.*;

/**
 * Created by chowii on 15/8/17.
 */

class MarketPlacePresenter implements
        MarketPlaceContract.ViewPresentable {

    private final CompositeDisposable compositeDisposable;
    private MarketPlaceContract.ViewInteractable interactable;
    private MarketplaceFilterWithSuburbs currentFilter;

    MarketPlacePresenter(MarketPlaceContract.ViewInteractable intractable){
        this.interactable = intractable;
        compositeDisposable = new CompositeDisposable();
    }

    // MARK: - ================== MarketPlaceContract.ViewPresentable ==================

    @Override
    public void createPresentation() {
        interactable.configureTabLayout();
    }

    @Override
    public void destroyPresentation() {
        interactable = null;
    }

    @Override
    public void startPresenting() {
        compositeDisposable.add(
                DEPENDENCIES.getDatabase().marketplaceFilterDao().getCurrentMarketplaceFilter()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(currentFilter ->
                                {
                                    this.currentFilter = currentFilter;
                                    loadData();
                                    interactable.configureViewForFilter(this.currentFilter);
                                },
                                error -> interactable.showError(error),
                                () ->
                                {
                                    if(this.currentFilter == null) {
                                        this.currentFilter = new MarketplaceFilterWithSuburbs();
                                        updateCurrentFilter();
                                        loadData();
                                        interactable.configureViewForFilter(this.currentFilter);
                                    }
                                })
        );
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    @Override
    public void onRefresh(){
        loadData();
    }

    @Override
    public void saleTypeChanged(String saleType) {
        currentFilter.getMarketplaceFilter().setSaleType(saleType);
        interactable.configureViewForFilter(currentFilter);
        updateCurrentFilter();
    }

    // MARK: - ================== General methods ==================

    private void loadData() {
        interactable.showRefreshing();
        compositeDisposable.add(
                DEPENDENCIES.getSohoService().searchProperties(createFilterParams())
                        .map(Converter::toBasicProperties)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(properties ->
                                {
                                    interactable.configureAdapter(properties);
                                    interactable.hideRefreshing();
                                }, throwable -> {
                                    interactable.hideRefreshing();
                                    interactable.showError(throwable);
                                }
                        )
        );
    }

    private void updateCurrentFilter() {
        compositeDisposable.add(
                SohoDatabaseKt.insertReactive(DEPENDENCIES.getDatabase(), currentFilter)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                insertedRowId -> loadData(),
                                error -> interactable.showError(error))
        );
    }

    private Map<String, Object> createFilterParams() {
        Map<String, Object> params = new QueryHashMap();
        params.put(FILTER_BY_LISTING_TYPE, currentFilter.getMarketplaceFilter().getSaleType());
        params.put(FILTER_BY_BEDROOM_COUNT, currentFilter.getMarketplaceFilter().getBedrooms());
        params.put(FILTER_BY_BATHROOM_COUNT, currentFilter.getMarketplaceFilter().getBathrooms());
        params.put(FILTER_BY_CARSPOT_COUNT, currentFilter.getMarketplaceFilter().getCarspots());
        params.put(FILTER_ALL_PROPERTIES, currentFilter.getMarketplaceFilter().getAllProperties());
        params.put(FILTER_BY_GOOGLE_PLACES, createGooglePlacesFilterParams());
        if(currentFilter.getMarketplaceFilter().getPriceFromBuy() != 0)
            params.put(FILTER_MIN_SALE_PRICE, currentFilter.getMarketplaceFilter().getPriceFromBuy());
        if(currentFilter.getMarketplaceFilter().getPriceToBuy() != 0)
            params.put(FILTER_MAX_SALE_PRICE, currentFilter.getMarketplaceFilter().getPriceToBuy());
        if(currentFilter.getMarketplaceFilter().getPriceFromRent() != 0)
            params.put(FILTER_MIN_RENT_PRICE, currentFilter.getMarketplaceFilter().getPriceFromRent());
        if(currentFilter.getMarketplaceFilter().getPriceToRent() != 0)
            params.put(FILTER_MAX_RENT_PRICE, currentFilter.getMarketplaceFilter().getPriceToRent());
        if(currentFilter.getMarketplaceFilter().getPropertyTypes() != null && currentFilter.getMarketplaceFilter().getPropertyTypes().size() != 0)
            params.put(FILTER_BY_PROPERTY_TYPE, currentFilter.getMarketplaceFilter().getPropertyTypes());
        return params;
    }

    private Map<String, Object> createGooglePlacesFilterParams() {
        Map<String, Object> params = new QueryHashMap();
        List<String> placeIds = new ArrayList<>();
        for(Suburb suburb: currentFilter.getSuburbs()) {
            placeIds.add(suburb.getPlaceId());
        }
        params.put(FILTER_PLACE_IDS, placeIds);
        params.put(FILTER_DISTANCE, currentFilter.getMarketplaceFilter().getRadius() * 1_000);
        return params;
    }
}
