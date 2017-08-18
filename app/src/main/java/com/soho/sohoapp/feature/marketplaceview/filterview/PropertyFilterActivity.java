package com.soho.sohoapp.feature.marketplaceview.filterview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.HeaderItem;
import com.soho.sohoapp.home.BaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 17/8/17.
 */

public class PropertyFilterActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.property_filter_filter_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.property_saved_filter_tab));

        final List<BaseModel> modelList = new ArrayList<>();

        modelList.add(new HeaderItem(getString(R.string.filter_suburb_search_header)));
        modelList.add(() -> R.layout.item_filter_search);

        modelList.add(new HeaderItem(getString(R.string.filter_radius_header)));
        modelList.add(() -> R.layout.item_filter_value_selector);

        modelList.add(new HeaderItem(getString(R.string.filter_price_range_header)));
        modelList.add(() -> R.layout.item_filter_range);


        modelList.add(new HeaderItem("Property Type"));
        modelList.add(() -> R.layout.item_filter_checkbox);


        recyclerView.setAdapter(new PropertyFilterAdapter(modelList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
