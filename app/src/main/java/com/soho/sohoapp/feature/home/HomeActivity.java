package com.soho.sohoapp.feature.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.more.MoreFragment;
import com.soho.sohoapp.feature.home.portfolio.PortfolioFragment;
import com.soho.sohoapp.feature.marketplaceview.components.MarketPlaceFragment;
import com.soho.sohoapp.helper.BottomNavigationViewHelper;
import com.soho.sohoapp.navigator.NavigatorImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class HomeActivity extends AppCompatActivity implements HomeContract.ViewInteractable {

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;
    private HomeContract.ViewPresentable presentable;
    private HomePresenter presenter;
    private List<String> addedFragmentsTags;

    public static Intent createIntent(Context context){
        return new Intent(context, HomeActivity.class);
    }

    public static Intent createIntent(Context context, int flags){
        return new Intent(context, HomeActivity.class).setFlags(flags);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initBottomNavigation();
        initAddedFragmentsTagList();

        presenter = new HomePresenter(this, NavigatorImpl.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        DEPENDENCIES.getLogger().d(item.getItemId() + "");
        switch(item.getItemId()){
            case R.id.home:
                DEPENDENCIES.getLogger().d("Profile Image button clicked in settings");
            case R.id.add_property_menu:
                NavigatorImpl.newInstance(this).openAddPropertyScreen();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_toolbar, menu);
        return true;
    }

    private void initBottomNavigation() {
        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_marketplace:
                    presentable.onMarketplaceClicked();
                    break;
                case R.id.tab_manage:
                    presentable.onManageClicked();
                    break;
                case R.id.tab_offers:
                    presentable.onOffersClicked();
                    break;
                case R.id.tab_more:
                    presentable.onMoreClicked();
                    break;
            }
            return true;
        });
    }

    @Override
    public void setPresentable(HomeContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public void showMarketplaceTab() {
        showFragment(MarketPlaceFragment.TAG);
    }

    @Override
    public void showManageTab() {
        showFragment(PortfolioFragment.TAG);
    }

    @Override
    public void showOffersTab() {
        //todo: MoreFragment is here temporary. We need to show Offers tab
        showFragment(MoreFragment.TAG);
    }

    @Override
    public void showMoreTab() {
        if(DEPENDENCIES.getPreferences().getAuthToken().isEmpty() && DEPENDENCIES.getPreferences().getMUser() == null){
            NavigatorImpl.newInstance(this).openLandingActivity();
        }else
        showFragment(MoreFragment.TAG);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @OnClick(R.id.addProperty)
    void onAddPropertyClicked() {
        presentable.onAddPropertyClicked();
    }

    private void showFragment(String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (String tag : addedFragmentsTags) {
            if (!fragmentTag.equals(tag)) {
                Fragment addedFragment = fragmentManager.findFragmentByTag(tag);
                if (addedFragment != null) {
                    transaction.hide(addedFragment);
                }
            }
        }

        Fragment existingFragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (existingFragment != null) {
            transaction.show(existingFragment);
        } else {
            transaction.add(R.id.container, createFragment(fragmentTag), fragmentTag);
        }
        transaction.commitAllowingStateLoss();
    }

    @Nullable
    private Fragment createFragment(String fragmentTag) {
        switch (fragmentTag) {
            case MarketPlaceFragment.TAG:
                return MarketPlaceFragment.newInstance();
            case PortfolioFragment.TAG:
                return PortfolioFragment.newInstance();
            case MoreFragment.TAG:
                return MoreFragment.Companion.newInstance();
            default:
                return null;
        }
    }

    private void initAddedFragmentsTagList() {
        addedFragmentsTags = new ArrayList<>();
        addedFragmentsTags.add(MarketPlaceFragment.TAG);
        addedFragmentsTags.add(PortfolioFragment.TAG);
        addedFragmentsTags.add(MoreFragment.TAG);
    }
}
