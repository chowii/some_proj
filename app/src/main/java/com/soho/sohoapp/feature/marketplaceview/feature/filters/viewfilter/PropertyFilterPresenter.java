package com.soho.sohoapp.feature.marketplaceview.feature.filters.viewfilter;


import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.database.SohoDatabaseKt;
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs;
import com.soho.sohoapp.database.entities.Suburb;
import com.soho.sohoapp.utils.Converter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

/**
 * Created by chowii on 22/8/17.
 */


class PropertyFilterPresenter implements AbsPresenter, PropertyFilterContract.ViewPresentable {

    private final PropertyFilterContract.ViewInteractable interactable;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MarketplaceFilterWithSuburbs getCurrentFilter() {
        return currentFilter;
    }

    private MarketplaceFilterWithSuburbs currentFilter;
    private List<Suburb> suburbsToDelete = new ArrayList<>();

    PropertyFilterPresenter(PropertyFilterContract.ViewInteractable interactable, MarketplaceFilterWithSuburbs currentFilter) {
        this.interactable = interactable;
        this.currentFilter = currentFilter;
    }

    void addSuburbToDelete(Suburb suburb) {
        if (suburbsToDelete == null)
            suburbsToDelete = new ArrayList<>();
        suburbsToDelete.add(suburb);
    }

    @Override
    public void startPresenting() {
        retrieveFilterFromApi();
    }

    public void applyFilters() {
        if (isValidData()) {
            currentFilter.getMarketplaceFilter().setCurrentFilter(true);
            compositeDisposable.add(SohoDatabaseKt.insertReactive(DEPENDENCIES.getDatabase(), currentFilter)
                    .map(insertedRowId -> DEPENDENCIES.getDatabase().suburbDao().removeAll(suburbsToDelete))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            insertedRowId -> interactable.finishApplyingFilters(),
                            interactable::showError
                    ));
        }
    }

    @Override
    public void retrieveFilterFromApi() {
        interactable.toggleLoadingIndicator(true);
        compositeDisposable.add(DEPENDENCIES.getSohoService().getPropertyTypes()
                .map(Converter::toPropertyTypeList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(list ->
                        {
                            interactable.populateForm(currentFilter, list);
                            interactable.toggleLoadingIndicator(false);
                        }, error ->
                        {
                            interactable.showError(error);
                            interactable.toggleLoadingIndicator(false);
                        }
                ));
    }

    @Override
    public void saveFilter() {
        compositeDisposable.add(SohoDatabaseKt.insertReactive(DEPENDENCIES.getDatabase(), new MarketplaceFilterWithSuburbs(currentFilter))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        insertedRowId -> interactable.showSaveSuccessful(),
                        interactable::showError
                ));
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }

    private boolean isValidData() {
        boolean dataIsValid = true;
        int fromPrice = currentFilter.getMarketplaceFilter().getFromPrice();
        int toPrice = currentFilter.getMarketplaceFilter().getToPrice();
        if (fromPrice != 0 && toPrice != 0 && fromPrice >= toPrice) {
            interactable.showToastMessage(R.string.filter_not_valid_price_range);
            dataIsValid = false;
        }
        return dataIsValid;
    }
}
