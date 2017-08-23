package com.soho.sohoapp.feature.marketplaceview.filterview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.home.BaseFormModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 17/8/17.
 */

public class PropertyFilterActivity extends AppCompatActivity implements PropertyFilterContract.ViewInteractable{

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    PropertyFilterPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        presenter = new PropertyFilterPresenter(this);
        presenter.startPresenting();
        presenter.retrieveFilterFromApi();

    }

    @Override
    public void configureTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.property_filter_filter_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.property_saved_filter_tab));
    }

    @Override
    public void configureAdapter(List<? extends BaseFormModel> formModelList) {

        recyclerView.setAdapter(new PropertyFilterAdapter(formModelList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
