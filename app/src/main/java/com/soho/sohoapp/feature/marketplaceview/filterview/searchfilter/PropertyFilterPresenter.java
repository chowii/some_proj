package com.soho.sohoapp.feature.marketplaceview.filterview.searchfilter;


import com.soho.sohoapp.abs.AbsPresenter;
import com.soho.sohoapp.feature.home.BaseFormModel;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.ButtonItem;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.FavouriteButtonItem;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.FilterCheckboxItem;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.FilterSearchItem;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.HeaderItem;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.RadioGroupView;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.RangeItem;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.ToggleItem;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.ValueSelectorItem;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.PropertyRoomItem;

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
    private final CompositeDisposable compositeDisposable;
    private List<BaseFormModel> modelList;

    PropertyFilterPresenter(PropertyFilterContract.ViewInteractable interactable) {
        this.interactable = interactable;
        compositeDisposable = new CompositeDisposable();

    }

    @Override
    public void startPresenting() {
        initViewList();
    }

    void initViewList() {
        modelList = new ArrayList<>();
        modelList.add(new HeaderItem<String>("ENTER SEARCH LOCATION, i.e. Areas, Suburbs"));
        modelList.add(new FilterSearchItem());

        modelList.add(new HeaderItem("Radius"));
        modelList.add(new ValueSelectorItem<Integer>());

        modelList.add(new HeaderItem("Range"));
        modelList.add(new RangeItem<ArrayList<String>>());


        modelList.add(new HeaderItem("Property Type"));

        modelList.add(new HeaderItem("Property Type"));
        modelList.add(new PropertyRoomItem());


        modelList.add(new HeaderItem("Property Status"));
        List<String> group = new ArrayList<>();
        group.add("All properties");
        group.add("Properties actively for Sale or Auction");
        RadioGroupView<String> v = new RadioGroupView(group);

        modelList.add(v);

        modelList.add(new HeaderItem(""));
        modelList.add(new ToggleItem("Surrounding suburb"));
        modelList.add(new FavouriteButtonItem("Save this search"));

        modelList.add(new ButtonItem("Search"));

        interactable.configureAdapter(modelList);
    }

    @Override
    public void retrieveFilterFromApi() {
        compositeDisposable.add(DEPENDENCIES.getSohoService().getPropertyTypesForFilter()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        list -> {
                            modelList.add(7, new FilterCheckboxItem("All"));
                            modelList.addAll(8, list);

                            interactable.configureAdapter(modelList);
                        },
                        throwable -> DEPENDENCIES.getLogger().d("filterThrowable " + throwable.getMessage())
                ));
    }

    @Override
    public void startPresenting(boolean fromConfigChanges) {
    }

    @Override
    public void stopPresenting() {
        compositeDisposable.clear();
    }
}
