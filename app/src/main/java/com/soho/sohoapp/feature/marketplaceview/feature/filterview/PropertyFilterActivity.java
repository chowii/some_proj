package com.soho.sohoapp.feature.marketplaceview.feature.filterview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.database.entities.MarketplaceFilter;
import com.soho.sohoapp.database.entities.MarketplaceFilterWithSuburbs;
import com.soho.sohoapp.feature.marketplaceview.filterview.savedfilters.PropertyFilterSavedFragment;
import com.soho.sohoapp.feature.marketplaceview.filterview.searchfilter.PropertyFilterViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

/**
 * Created by chowii on 17/8/17.
 */

public class PropertyFilterActivity extends AbsActivity
        implements
        TabLayout.OnTabSelectedListener,
        PropertyFilterSavedFragment.OnFilterSelectedCallback
{

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    MarketplaceFilterWithSuburbs currentFilter;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        retrieveCurrentFilter();
        tabLayout.addTab(tabLayout.newTab().setText(R.string.property_filter_filter_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.property_saved_filter_tab));
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void retrieveCurrentFilter() {
        compositeDisposable.add(
                DEPENDENCIES.getDatabase().marketplaceFilterDao().getCurrentMarketplaceFilter()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(currentFilter ->
                                {
                                    this.currentFilter = currentFilter;
                                    replaceFilterViewFragment(PropertyFilterViewFragment.newInstance(this.currentFilter));
                                },
                                error -> handleError(error),
                                () ->
                                {
                                    if(this.currentFilter == null) {
                                        this.currentFilter = new MarketplaceFilterWithSuburbs();
                                        replaceFilterViewFragment(PropertyFilterViewFragment.newInstance(this.currentFilter));
                                    }
                                })
        );
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) replaceFilterViewFragment(PropertyFilterViewFragment.newInstance(currentFilter));
        else {
            PropertyFilterSavedFragment fragment = PropertyFilterSavedFragment.newInstance();
            fragment.setOnFilterSelectedCallback(this);
            replaceFragment(fragment);
        }
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.filter_fragment_container, fragment)
                .commit();
    }

    private void replaceFilterViewFragment(PropertyFilterViewFragment fragment){
        replaceFragment(fragment);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onFilterSelected() {
        replaceFilterViewFragment(PropertyFilterViewFragment.newInstance(currentFilter));
        tabLayout.getTabAt(0).select();
    }
}
