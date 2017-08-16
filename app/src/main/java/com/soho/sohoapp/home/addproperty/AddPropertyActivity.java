package com.soho.sohoapp.home.addproperty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.home.addproperty.address.AddressFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPropertyActivity extends AbsActivity implements AddPropertyContract.View, AddressFragment.Listener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AddPropertyPresenter presenter;
    private AddPropertyContract.ViewActionsListener actionsListener;
    private Fragment addressFragment;
    private Fragment relationFragment;

    @NonNull
    public static Intent createIntent(Context context) {
        return new Intent(context, AddPropertyActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(v -> closeCurrentScreen());

        presenter = new AddPropertyPresenter(this);
        presenter.startPresenting();
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        closeCurrentScreen();
    }

    @Override
    public void setActionsListener(AddPropertyContract.ViewActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    @Override
    public void showAddressFragment() {
        if (addressFragment == null) {
            addressFragment = AddressFragment.newInstance();
        }
        showFragment(addressFragment, AddressFragment.TAG);
    }

    @Override
    public void showRelationFragment() {
//        if (relationFragment == null) {
//            relationFragment = RelationFragment.newInstance();
//        }
//        showFragment(relationFragment, RelationFragment.TAG);
    }

    private void showFragment(Fragment fragment, String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(fragmentTag)
                .commitAllowingStateLoss();
    }

    private void closeCurrentScreen() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        } else {
            supportFragmentManager.popBackStack();
        }
    }

    @Override
    public void OnAddressSelected(PropertyAddress propertyAddress) {
        System.out.println("Address: " + propertyAddress.getFullAddress());
        System.out.println("Address: " + propertyAddress.getLat());
        System.out.println("Address: " + propertyAddress.getLng());
    }
}
