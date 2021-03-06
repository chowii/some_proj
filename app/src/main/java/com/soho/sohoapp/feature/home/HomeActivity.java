package com.soho.sohoapp.feature.home;

import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.elasticode.model.ElasticodeSessionParams;
import com.elasticode.provider.Elasticode;
import com.elasticode.provider.callback.ElasticodeResponse;
import com.soho.sohoapp.BuildConfig;
import com.soho.sohoapp.R;
import com.soho.sohoapp.SohoApplication;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.feature.chat.ChatChannelFragment;
import com.soho.sohoapp.feature.home.more.MoreFragment;
import com.soho.sohoapp.feature.home.portfolio.PortfolioFragment;
import com.soho.sohoapp.feature.marketplaceview.components.MarketPlaceFragment;
import com.soho.sohoapp.helper.BottomNavigationViewHelper;
import com.soho.sohoapp.navigator.NavigatorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.pm.PackageManager.GET_META_DATA;
import static com.soho.sohoapp.Dependencies.DEPENDENCIES;
import static com.soho.sohoapp.network.Keys.DeeplinkingNotifications.ACTION_EDIT;
import static com.soho.sohoapp.network.Keys.DeeplinkingNotifications.ACTION_VIEW;
import static com.soho.sohoapp.network.Keys.DeeplinkingNotifications.AUTH_PROPERTIES;

public class HomeActivity extends AbsActivity implements HomeContract.ViewInteractable {

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;
    private HomeContract.ViewPresentable presentable;
    private HomePresenter presenter;
    private List<String> addedFragmentsTags;
    private NavigatorImpl navigator;

    public static Intent createIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    public static Intent createIntent(Context context, int flags) {
        return new Intent(context, HomeActivity.class).setFlags(flags);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        String apiKey = "";
        try {
            ApplicationInfo applicationInfo = SohoApplication.getContext()
                    .getPackageManager()
                    .getApplicationInfo(this.getPackageName(), GET_META_DATA);
            apiKey = applicationInfo.metaData.getString("elastico.ApiKey", "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Elasticode elasticode = Elasticode.getInstance(this, apiKey, elasticodeObserver);
        elasticode.setSessionParams(new ElasticodeSessionParams(!BuildConfig.DEBUG, this));
        elasticode.ready();

        navigator = NavigatorImpl.newInstance(this);
        initBottomNavigation();
        initAddedFragmentsTagList();

        presenter = new HomePresenter(this, NavigatorImpl.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);

        checkDeepLinking();
    }

    private void checkDeepLinking() {
        Uri uri = getIntent().getData();
        if (uri == null) {
            return;
        }
        final int EDIT_PROPERTY = 10;
        final int VIEW_PROPERTY = 11;
        final UriMatcher sohoURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sohoURIMatcher.addURI(AUTH_PROPERTIES, "/#/" + ACTION_EDIT, EDIT_PROPERTY);
        sohoURIMatcher.addURI(AUTH_PROPERTIES, "/#/" + ACTION_VIEW, VIEW_PROPERTY);
        int match = sohoURIMatcher.match(uri);
        int propertyId;
        switch (match) {
            case EDIT_PROPERTY:
                propertyId = getPropertyId(uri);
                if (propertyId > 0) {
                    navigator.openEditPropertyScreen(propertyId);
                }
                break;
            case VIEW_PROPERTY:
                propertyId = getPropertyId(uri);
                if (propertyId > 0) {
                    navigator.openPropertyDetailScreen(propertyId);
                }
                break;
        }
    }

    private int getPropertyId(Uri uri) {
        try {
            return Integer.valueOf(uri.getPathSegments().get(0));
        } catch (NumberFormatException nfe) {
            DEPENDENCIES.getLogger().e(nfe.getMessage(), nfe);
        }
        return -1;
    }


    private Observer elasticodeObserver = (observable, data) -> {
        if (data instanceof ElasticodeResponse) {
            ElasticodeResponse response = (ElasticodeResponse) data;
            if (response.getError() != null) {
                // In case of error
            } else {
                switch (response.getType()) {
                    case ON_LAUNCH_DISPLAYED:
                        Boolean didApeared = ((Boolean) response.getAdditionalData());
                        break;
                    // put here all types (ElasticodeResponseType) which you want to handle
                }
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DEPENDENCIES.getLogger().d(String.valueOf(item.getItemId()));
        switch (item.getItemId()) {
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
        showFragment(ChatChannelFragment.TAG);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void showMoreTab() {
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
            case ChatChannelFragment.TAG:
                return ChatChannelFragment.Companion.newInstance();
            default:
                return null;
        }
    }

    private void initAddedFragmentsTagList() {
        addedFragmentsTags = new ArrayList<>();
        addedFragmentsTags.add(MarketPlaceFragment.TAG);
        addedFragmentsTags.add(PortfolioFragment.TAG);
        addedFragmentsTags.add(MoreFragment.TAG);
        addedFragmentsTags.add(ChatChannelFragment.TAG);
    }
}
