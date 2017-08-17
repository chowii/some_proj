package com.soho.sohoapp.home.addproperty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.data.PropertyRole;
import com.soho.sohoapp.data.PropertyType;
import com.soho.sohoapp.home.addproperty.address.AddressFragment;
import com.soho.sohoapp.home.addproperty.relation.RelationFragment;
import com.soho.sohoapp.home.addproperty.type.PropertyTypeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPropertyActivity extends AbsActivity implements
        AddPropertyContract.View,
        AddressFragment.Listener,
        RelationFragment.Listener,
        PropertyTypeFragment.Listener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AddPropertyPresenter presenter;
    private AddPropertyContract.ViewActionsListener actionsListener;

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
        presenter.startPresenting(savedInstanceState != null);
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
        showFragment(AddressFragment.newInstance(), AddressFragment.TAG);
    }

    @Override
    public void showRelationFragment() {
        hideKeyboard();
        showFragment(RelationFragment.newInstance(), RelationFragment.TAG);
    }

    @Override
    public void showPropertyTypeFragment() {
        showFragment(PropertyTypeFragment.newInstance(), PropertyTypeFragment.TAG);
    }

    @Override
    public void onAddressSelected(PropertyAddress propertyAddress) {
        actionsListener.onAddressSelected(propertyAddress);
    }

    @Override
    public void onPropertyRoleSelected(PropertyRole propertyRole) {
        actionsListener.onPropertyRoleSelected(propertyRole);
    }

    @Override
    public void onPropertyTypeSelected(PropertyType propertyType) {
        actionsListener.onPropertyTypeSelected(propertyType);
    }

    private void showFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragmentByTag == null) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fragment_open_anim, R.anim.fragment_close_anim)
                    .replace(R.id.container, fragment)
                    .addToBackStack(fragmentTag)
                    .commitAllowingStateLoss();
        }
    }

    private void closeCurrentScreen() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        } else {
            supportFragmentManager.popBackStack();
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

}
