package com.soho.sohoapp.feature.marketplaceview.filterview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.CheckboxTitle;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.FilterSearchItem;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.HeaderItem;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.RadioGroupView;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.RangeItem;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.ValueSelectorItem;
import com.soho.sohoapp.home.BaseFormModel;

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

        final List<BaseFormModel> modelList = new ArrayList<>();

        modelList.add(new HeaderItem<String>(getString(R.string.filter_suburb_search_header)));
        modelList.add(new FilterSearchItem());

        modelList.add(new HeaderItem(getString(R.string.filter_radius_header)));
        modelList.add(new ValueSelectorItem<Integer>());

        modelList.add(new HeaderItem(getString(R.string.filter_price_range_header)));
        modelList.add(new RangeItem<ArrayList<String>>());


        modelList.add(new HeaderItem("Property Type"));
        CheckboxTitle checkboxTitle = new CheckboxTitle("All");
        modelList.add(new CheckboxTitle("All"));
        modelList.add(new CheckboxTitle("House"));
        modelList.add(new CheckboxTitle("Unit or Apartment"));
        modelList.add(new CheckboxTitle("Duplex or Semi"));
        modelList.add(new CheckboxTitle("Terrace"));
        modelList.add(new CheckboxTitle("Condo"));

        modelList.add(new HeaderItem("Property Status"));

        List<String> group = new ArrayList<>();
        group.add("All properties");
        group.add("Properties actively for Sale or Auction");
        RadioGroupView<String> v = new RadioGroupView(group);
        modelList.add(v);


        recyclerView.setAdapter(new PropertyFilterAdapter(modelList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
