package com.soho.sohoapp.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.soho.sohoapp.R;
import com.soho.sohoapp.helper.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.bottom_navigation_view) BottomNavigationView mBottomNavigationView;

    private HomePagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        setupViewPagerAdapter();
    }

    private void setupViewPagerAdapter() {
        if(mAdapter == null) {
            mAdapter = new HomePagerAdapter(getSupportFragmentManager());
        }
        mViewPager.setAdapter(mAdapter);
    }

    // MARK: - ================== Actions ==================

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int index = Character.getNumericValue(item.getNumericShortcut());
            if(index >= 0) {
                mViewPager.setCurrentItem(index);
                return true;
            }
            return false;
        }

    };

    @OnPageChange(R.id.viewpager)
    void onPageChanged(int page) {
        mBottomNavigationView.setSelectedItemId(mBottomNavigationView.getMenu().getItem(page > 1 ? page + 1: page).getItemId());
    }

    @OnClick(R.id.btn_add_property)
    void onAddPropertyClicked(View view) {
        //TODO: Show add property flow @Julia
    }
}
