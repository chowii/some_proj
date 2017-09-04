package com.soho.sohoapp.feature.home.addproperty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.dialogs.LoadingDialog;
import com.soho.sohoapp.feature.home.addproperty.address.AddressFragment;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyAddress;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyRole;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.feature.home.addproperty.investment.InvestmentFragment;
import com.soho.sohoapp.feature.home.addproperty.relation.RelationFragment;
import com.soho.sohoapp.feature.home.addproperty.rooms.RoomsFragment;
import com.soho.sohoapp.feature.home.addproperty.type.PropertyTypeFragment;
import com.soho.sohoapp.navigator.AndroidNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPropertyActivity extends AbsActivity implements
        AddPropertyContract.View,
        AddressFragment.Listener,
        RelationFragment.Listener,
        PropertyTypeFragment.Listener,
        InvestmentFragment.Listener,
        RoomsFragment.Listener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AddPropertyPresenter presenter;
    private AddPropertyContract.ViewActionsListener actionsListener;
    private LoadingDialog loadingDialog;

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

        presenter = new AddPropertyPresenter(this, AndroidNavigator.newInstance(this));
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
        showFragment(RelationFragment.newInstance(), RelationFragment.TAG);
    }

    @Override
    public void showPropertyTypeFragment() {
        showFragment(PropertyTypeFragment.newInstance(), PropertyTypeFragment.TAG);
    }

    @Override
    public void showInvestmentFragment(boolean forOwner) {
        showFragment(InvestmentFragment.newInstance(forOwner), InvestmentFragment.TAG);
    }

    @Override
    public void showRoomsFragment() {
        showFragment(RoomsFragment.newInstance(), RoomsFragment.TAG);
    }

    @Override
    public void showMessage(String s) {
        showToast(s);
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

    @Override
    public void onHomeOrInvestmentSelected(boolean isInvestment) {
        actionsListener.onHomeOrInvestmentSelected(isInvestment);
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }


    private void showFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragmentByTag == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            //animations is not needed for first fragment
            if (fragmentManager.getBackStackEntryCount() > 0) {
                transaction.setCustomAnimations(R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right);
            }

            transaction.replace(R.id.container, fragment)
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

    @Override
    public void onRoomsSelected(int bedrooms, int bathrooms, int carspots) {
        actionsListener.onRoomsSelected(bedrooms, bathrooms, carspots);
    }
}
