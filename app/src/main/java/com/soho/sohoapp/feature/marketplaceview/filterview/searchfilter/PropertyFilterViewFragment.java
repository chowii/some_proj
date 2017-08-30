package com.soho.sohoapp.feature.marketplaceview.filterview.searchfilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseFormModel;
import com.soho.sohoapp.home.HomeActivity;
import com.soho.sohoapp.landing.BaseFragment;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 27/08/17.
 */

public class PropertyFilterViewFragment extends BaseFragment
        implements
        PropertyFilterContract.ViewInteractable,
        PropertyFilterAdapter.OnSearchClickListener
{

    public static PropertyFilterViewFragment newInstance() {
        PropertyFilterViewFragment fragment = new PropertyFilterViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    PropertyFilterPresenter presenter;
    private boolean isBuySection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        ButterKnife.bind(this, view);
        presenter = new PropertyFilterPresenter(this);
        presenter.startPresenting();
        presenter.retrieveFilterFromApi();
        return view;
    }

    @Override
    public void configureAdapter(List<? extends BaseFormModel> formModelList) {
        recyclerView.setAdapter(new PropertyFilterAdapter(formModelList, getActivity(), this, isBuySection));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void isBuySection(boolean isBuySection){ this.isBuySection = isBuySection; }

    @Override
    public void onSearchClicked(Map<String, Object> searchParams) {
        Intent i = new Intent(getActivity(), HomeActivity.class);
        i.putExtra("searchParams", (Serializable) searchParams);
        startActivity(i);
    }
}
