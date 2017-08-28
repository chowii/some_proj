package com.soho.sohoapp.home.editproperty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.navigator.AndroidNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPropertyActivity extends AbsActivity implements EditPropertyContract.View {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.imageViewPager)
    ViewPager imageViewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private EditPropertyContract.ViewActionsListener actionsListener;
    private EditPropertyPresenter presenter;

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull PortfolioProperty property) {
        return new Intent(context, EditPropertyActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);
        ButterKnife.bind(this);

        initToolbar();
        initTabs();

        presenter = new EditPropertyPresenter(this, AndroidNavigator.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @Override
    public void setActionsListener(EditPropertyContract.ViewActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.edit_property_toolbar);
        toolbar.setNavigationOnClickListener(view -> actionsListener.onBackClicked());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_add_photo:
                    actionsListener.onAddPhotoClicked();
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    private void initTabs() {
        EditPropertyTabsAdapter adapter = new EditPropertyTabsAdapter(this, getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        ImageHeaderViewPager pagerAdapter = new ImageHeaderViewPager(imageViewPager.getContext());
        imageViewPager.setAdapter(pagerAdapter);
    }
}
