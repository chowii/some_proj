package com.soho.sohoapp.feature.marketplaceview.components;

import android.content.Context;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.dtos.BasicPropertyResult;
import com.soho.sohoapp.data.models.PaginationInformation;
import com.soho.sohoapp.database.SohoDatabaseKt;
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs;
import com.soho.sohoapp.database.entities.Suburb;
import com.soho.sohoapp.extensions.ResponseExtKt;
import com.soho.sohoapp.network.Keys;
import com.soho.sohoapp.utils.Converter;
import com.soho.sohoapp.utils.QueryHashMap;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;
import static com.soho.sohoapp.data.enums.MarketplaceFilterSaleType.SALE;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_ALL_PROPERTIES;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_BY_BATHROOM_COUNT;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_BY_BEDROOM_COUNT;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_BY_CARSPOT_COUNT;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_BY_GOOGLE_PLACES;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_BY_LISTING_TYPE;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_BY_ORDER;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_BY_PROPERTY_TYPE;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_COLUMN;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_DIRECTION;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_DISTANCE;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_MAX_RENT_PRICE;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_MAX_SALE_PRICE;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_MIN_RENT_PRICE;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_MIN_SALE_PRICE;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_PAGE;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_PER_PAGE;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_PLACE_IDS;
import static com.soho.sohoapp.network.Keys.Filter.FILTER_RENT_FREQUENCY;

/**
 * Created by chowii on 15/8/17.
 */

class MarketPlacePresenter implements
        MarketPlaceContract.ViewPresentable {

    private final int PER_PAGE = 20;
    public static final int FIRST_PAGE = 1;

    private final CompositeDisposable compositeDisposable;
    private final Context context;
    private MarketPlaceContract.ViewInteractable interactable;
    private MarketplaceFilterWithSuburbs currentFilter;
    private PaginationInformation paginationInformation;

    MarketPlacePresenter(MarketPlaceContract.ViewInteractable intractable
            , Context context) {
        this.interactable = intractable;
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    // MARK: - ================== MarketPlaceContract.ViewPresentable ==================

    @Override
    public void createPresentation() {
        interactable.configureTabLayout();
    }

    @Override
    public void startPresenting() {
        interactable.setPresentable(this);
        retrieveFiltersAndPerformFullRefresh();
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    @Override
    public void retrieveFiltersAndPerformFullRefresh() {
        compositeDisposable.add(
                DEPENDENCIES.getDatabase().marketplaceFilterDao().getCurrentMarketplaceFilter()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(currentFilter ->
                                {
                                    this.currentFilter = currentFilter;
                                    performFullRefresh();
                                    interactable.configureViewForFilter(this.currentFilter);
                                },
                                error -> interactable.showError(error),
                                () ->
                                {
                                    if (this.currentFilter == null) {
                                        this.currentFilter = new MarketplaceFilterWithSuburbs();
                                        this.currentFilter.getMarketplaceFilter().setCurrentFilter(true);
                                        updateCurrentFilter();
                                        performFullRefresh();
                                        interactable.configureViewForFilter(this.currentFilter);
                                    }
                                })
        );
    }

    @Override
    public void performFullRefresh() {
        interactable.showProgressBar();
        requestPropertiesWithParams(createFilterParams(FIRST_PAGE),
                response -> {
                    paginationInformation = ResponseExtKt.getPaginationInformation(response);
                    interactable.hideProgressBar();
                    interactable.fullRefreshComplete(Converter.toBasicProperties(response.body()),
                            ResponseExtKt.getPaginationInformation(response));
                });
    }

    @Override
    public void fetchNextPage(int page) {
        requestPropertiesWithParams(createFilterParams(page),
                response -> {
                    paginationInformation = ResponseExtKt.getPaginationInformation(response);
                    interactable.hideProgressBar();
                    interactable.nextPageLoaded(Converter.toBasicProperties(response.body()),
                            ResponseExtKt.getPaginationInformation(response));
                });
    }

    private void requestPropertiesWithParams(QueryHashMap params, Consumer<Response<List<BasicPropertyResult>>> onSuccess) {
        compositeDisposable.add(
                DEPENDENCIES.getSohoService().searchProperties(params)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(onSuccess,
                                throwable ->
                                {
                                    interactable.hideProgressBar();
                                    interactable.showError(throwable);
                                }
                        )
        );
    }

    @Override
    public void saleTypeChanged(String saleType) {
        currentFilter.getMarketplaceFilter().setSaleType(saleType);
        interactable.configureViewForFilter(currentFilter);
        updateCurrentFilter();
    }

    @Override
    public void showOrderDialog(String saleType) {
        new OrderPropertyDialog(context).show(type ->
        {
            currentFilter.getMarketplaceFilter().setSaleType(saleType);
            String rentValueType = OrderPropertyDialog.FilterType.HighToLowRent.INSTANCE.getOrderType();
            String saleValueType = OrderPropertyDialog.FilterType.HighToLowSale.INSTANCE.getOrderType();

            String orderByTypeForMoneyValue = saleType.equalsIgnoreCase(SALE) ? saleValueType : rentValueType;

            switch (type.getStringRes()) {
                case R.string.market_order_low_to_high: {
                    currentFilter.getMarketplaceFilter().setOrderByType(orderByTypeForMoneyValue);
                    currentFilter.getMarketplaceFilter().setOrderDirection(Keys.OrderDirection.ASCENDING);
                    break;
                }
                case R.string.market_order_high_to_low: {
                    currentFilter.getMarketplaceFilter().setOrderByType(orderByTypeForMoneyValue);
                    currentFilter.getMarketplaceFilter().setOrderDirection(Keys.OrderDirection.DESCENDING);
                    break;
                }
                case R.string.market_order_oldest_to_newest: {
                    currentFilter.getMarketplaceFilter().setOrderDirection(Keys.OrderDirection.ASCENDING);
                    currentFilter.getMarketplaceFilter().setOrderByType(type.getOrderType());
                    break;
                }
                case R.string.market_order_newest_to_oldest: {
                    currentFilter.getMarketplaceFilter().setOrderDirection(Keys.OrderDirection.DESCENDING);
                    currentFilter.getMarketplaceFilter().setOrderByType(type.getOrderType());
                    break;
                }
            }
            updateCurrentFilter();
        });
    }
    // MARK: - ================== General methods ==================

    private void updateCurrentFilter() {
        compositeDisposable.add(
                SohoDatabaseKt.insertReactive(DEPENDENCIES.getDatabase(), currentFilter)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                insertedRowId -> retrieveFiltersAndPerformFullRefresh(),
                                error -> interactable.showError(error))
        );
    }

    // MARK: - ================== Params related ==================

    private QueryHashMap createFilterParams(int page) {
        QueryHashMap params = new QueryHashMap()
                .put(FILTER_PAGE, page)
                .put(FILTER_PER_PAGE, PER_PAGE)
                .put(FILTER_BY_LISTING_TYPE, currentFilter.getMarketplaceFilter().getSaleType())
                .put(FILTER_BY_BEDROOM_COUNT, currentFilter.getMarketplaceFilter().getBedrooms())
                .put(FILTER_BY_BATHROOM_COUNT, currentFilter.getMarketplaceFilter().getBathrooms())
                .put(FILTER_BY_CARSPOT_COUNT, currentFilter.getMarketplaceFilter().getCarspots())
                .put(FILTER_ALL_PROPERTIES, currentFilter.getMarketplaceFilter().getAllProperties())
                .put(FILTER_BY_ORDER, createOrderByParams(currentFilter.getMarketplaceFilter().getOrderByType()
                        , currentFilter.getMarketplaceFilter().getOrderDirection()))
                .put(FILTER_BY_GOOGLE_PLACES, createGooglePlacesFilterParams());

        if (currentFilter.getMarketplaceFilter().getPropertyTypes() != null && currentFilter.getMarketplaceFilter().getPropertyTypes().size() != 0)
            params.put(FILTER_BY_PROPERTY_TYPE, currentFilter.getMarketplaceFilter().getPropertyTypes());
        if (currentFilter.getMarketplaceFilter().isRentFilter())
            updateForRentParams(params);
        else if (currentFilter.getMarketplaceFilter().isSaleFilter())
            updateForSaleParams(params);
        return params;
    }

    private void updateForSaleParams(QueryHashMap params) {
        if (currentFilter.getMarketplaceFilter().getPriceFromBuy() != 0)
            params.put(FILTER_MIN_SALE_PRICE, currentFilter.getMarketplaceFilter().getPriceFromBuy());
        if (currentFilter.getMarketplaceFilter().getPriceToBuy() != 0)
            params.put(FILTER_MAX_SALE_PRICE, currentFilter.getMarketplaceFilter().getPriceToBuy());
    }

    private void updateForRentParams(QueryHashMap params) {
        params.put(FILTER_RENT_FREQUENCY, currentFilter.getMarketplaceFilter().getRentPaymentFrequency());
        if (currentFilter.getMarketplaceFilter().getPriceFromRent() != 0)
            params.put(FILTER_MIN_RENT_PRICE, currentFilter.getMarketplaceFilter().getPriceFromRent());
        if (currentFilter.getMarketplaceFilter().getPriceToRent() != 0)
            params.put(FILTER_MAX_RENT_PRICE, currentFilter.getMarketplaceFilter().getPriceToRent());
    }

    private QueryHashMap createGooglePlacesFilterParams() {
        QueryHashMap params = new QueryHashMap()
                .put(FILTER_DISTANCE, currentFilter.getMarketplaceFilter().getRadius() * 1_000);

        List<String> placeIds = new ArrayList<>();
        for (Suburb suburb : currentFilter.getSuburbs()) {
            placeIds.add(suburb.getPlaceId());
        }
        params.put(FILTER_PLACE_IDS, placeIds);
        return params;
    }

    private QueryHashMap createOrderByParams(String orderByType, String orderDirection) {
        return new QueryHashMap()
                .put(FILTER_COLUMN, orderByType)
                .put(FILTER_DIRECTION, orderDirection);
    }
}
