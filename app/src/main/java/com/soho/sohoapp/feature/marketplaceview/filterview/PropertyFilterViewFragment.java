package com.soho.sohoapp.feature.marketplaceview.filterview;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseFormModel;
import com.soho.sohoapp.landing.BaseFragment;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 27/08/17.
 */

public class PropertyFilterViewFragment extends BaseFragment implements PropertyFilterContract.ViewInteractable
{

    public static Fragment newInstance() {
        PropertyFilterViewFragment fragment = new PropertyFilterViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    PropertyFilterPresenter presenter;

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
        recyclerView.setAdapter(new PropertyFilterAdapter(formModelList));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
