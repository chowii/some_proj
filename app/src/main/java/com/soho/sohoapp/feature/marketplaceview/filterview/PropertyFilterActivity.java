package com.soho.sohoapp.feature.marketplaceview.filterview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.soho.sohoapp.R;

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

    private boolean isBuySection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        isBuySection = getIntent().getExtras().getBoolean("is_buy_section", true);
        replaceFilterViewFrgament(PropertyFilterViewFragment.newInstance(), isBuySection);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.property_filter_filter_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.property_saved_filter_tab));
        tabLayout.addOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getPosition() == 0) replaceFilterViewFrgament(PropertyFilterViewFragment.newInstance(), isBuySection);
        else replaceFragment(PropertyFilterSavedFragment.newInstance());
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.filter_fragment_container, fragment)
                .commit();
    }

    private void replaceFilterViewFrgament(PropertyFilterViewFragment fragment, boolean isBuySection){
        fragment.isBuySection(isBuySection);
        replaceFragment(fragment);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
}
