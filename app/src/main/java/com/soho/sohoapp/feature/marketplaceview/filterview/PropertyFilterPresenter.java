package com.soho.sohoapp.feature.marketplaceview.filterview;


import android.util.Log;

import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.*;
import com.soho.sohoapp.home.BaseFormModel;
import com.soho.sohoapp.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.feature.marketplaceview.filterview.PropertyFilterContract.ViewInteractable;

/**
 * Created by chowii on 22/8/17.
 */



public class PropertyFilterPresenter implements PropertyFilterContract.ViewPresentable {

    private final ViewInteractable interactable;

    public PropertyFilterPresenter(ViewInteractable interactable) {
        this.interactable = interactable;
    }

    @Override
    public void startPresenting() {
        initViewList();
    }
        List<BaseFormModel> modelList;

    void initViewList(){
        modelList = new ArrayList<>();
        modelList.add(new HeaderItem<String>("ENTER SEARCH LOCATION, i.e. Areas, Suburbs"));
        modelList.add(new FilterSearchItem());

        modelList.add(new HeaderItem("Radius"));
        modelList.add(new ValueSelectorItem<Integer>());

        modelList.add(new HeaderItem("Range"));
        modelList.add(new RangeItem<ArrayList<String>>());


        modelList.add(new HeaderItem("Property Type"));

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
        ApiClient.getService().getPropertyTypes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        list -> {
                            modelList.add(7, new FilterCheckboxItem("All"));
                            modelList.addAll(8, list);

                            interactable.configureAdapter(modelList);
                        },
                        throwable ->
                            Log.v("LOG_TAG---","throwable" + throwable.getMessage())
                );
    }

    @Override
    public void stopPresenting() {

    }
}
