package com.soho.sohoapp.feature.marketplaceview.filterview.savedfilters;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.soho.sohoapp.Constants;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.HeaderItem;
import com.soho.sohoapp.feature.marketplaceview.model.EmptyDataSetItem;
import com.soho.sohoapp.helper.FileWriter;
import com.soho.sohoapp.landing.BaseFragment;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 25/8/17.
 */

public class PropertyFilterSavedFragment extends BaseFragment implements PropertyFilterSavedAdapter.OnFilterItemClickedListener {



    public static PropertyFilterSavedFragment newInstance() {
        PropertyFilterSavedFragment fragment = new PropertyFilterSavedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private OnFilterSelectedCallback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_saved, container, false);
        ButterKnife.bind(this, view);

        List<BaseModel> filterList = new ArrayList<>();
        Type type = new TypeToken<SavedFilterList>(){}.getType();
        SavedFilterList list = (SavedFilterList) FileWriter.readFileFromDevice(getContext(), Constants.getSavedFilter(), type);

        filterList.add(new HeaderItem<String>("Saved Search"));
        filterList.addAll(list.getData());

        if (filterList.size() < 2) filterList.add(new EmptyDataSetItem());

        recyclerView.setAdapter(new PropertyFilterSavedAdapter(filterList, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onFilterItemClicked() {
        callback.onFilterSelected();
    }

    public void setOnFilterSelectedCallback(OnFilterSelectedCallback callback) {
        this.callback = callback;
    }

    public interface OnFilterSelectedCallback{
        void onFilterSelected();
    }
}
