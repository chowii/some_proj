package com.soho.sohoapp.feature.marketplaceview.filterview.savedfilters;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.soho.sohoapp.Constants;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.HeaderItem;
import com.soho.sohoapp.feature.marketplaceview.filterview.savedfilters.model.SavedFilterItem;
import com.soho.sohoapp.feature.marketplaceview.model.EmptyDataSetItem;
import com.soho.sohoapp.helper.FileWriter;
import com.soho.sohoapp.home.BaseModel;
import com.soho.sohoapp.landing.BaseFragment;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 25/8/17.
 */

public class PropertyFilterSavedFragment extends BaseFragment {

    public static PropertyFilterSavedFragment newInstance() {
        PropertyFilterSavedFragment fragment = new PropertyFilterSavedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_saved, container, false);
        ButterKnife.bind(this, view);

        JSONObject jsonObject = FileWriter.readFileFromDevice(getActivity(), Constants.getSavedFilter());
        List<BaseModel> mapList = new ArrayList<>();

        mapList.add(new HeaderItem<String>("Saved Search"));

        Gson gson = new Gson();
        SavedFilterList l = gson.fromJson(jsonObject.toString(), SavedFilterList.class);
        List<SavedFilterItem> savedItem = l.getData();
        mapList.addAll(savedItem);

        if(mapList.size() < 2) mapList.add(new EmptyDataSetItem());
        recyclerView.setAdapter(new PropertyFilterSavedAdapter(mapList));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
