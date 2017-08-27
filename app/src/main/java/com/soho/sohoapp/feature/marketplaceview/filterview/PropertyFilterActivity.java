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

public class PropertyFilterActivity extends AppCompatActivity
        implements
        TabLayout.OnTabSelectedListener
{

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    PropertyFilterPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.filter_fragment_container, PropertyFilterViewFragment.newInstance())
                .commit();
        tabLayout.addTab(tabLayout.newTab().setText(R.string.property_filter_filter_tab));

        tabLayout.addTab(tabLayout.newTab().setText(R.string.property_saved_filter_tab));

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getPosition() == 0){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.filter_fragment_container, PropertyFilterViewFragment.newInstance())
                    .commit();
        }else
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.filter_fragment_container, PropertyFilterViewFragment.newInstance())
                    .commit();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
}
