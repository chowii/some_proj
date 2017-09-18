package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.navigator.NavigatorImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AutocompleteAddressActivity extends AbsActivity implements AutocompleteAddressContract.ViewInteractable {
    private AutocompleteAddressContract.ViewPresentable presentable;
    private AutocompleteAddressPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @NonNull
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, AutocompleteAddressActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete_address);
        ButterKnife.bind(this);

        initToolbar();

        presenter = new AutocompleteAddressPresenter(this, NavigatorImpl.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @Override
    public void setPresentable(AutocompleteAddressContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.autocomplete_address_toolbar);
        toolbar.setNavigationOnClickListener(view -> presentable.onBackClicked());
        toolbar.setOnMenuItemClickListener(item -> {
            if (R.id.action_add_photo == item.getItemId()) {
                presentable.onDoneClicked();
            }
            return false;
        });
    }
}
