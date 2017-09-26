package com.soho.sohoapp.feature.home.editproperty.publish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.extensions.ActivityExtKt;
import com.soho.sohoapp.navigator.NavigatorImpl;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyDescriptionActivity extends AbsActivity implements PropertyDescriptionContract.ViewInteractable {
    private static final String KEY_DESCRIPTION = "KEY_DESCRIPTION";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.description)
    EditText description;

    private PropertyDescriptionPresenter presenter;
    private PropertyDescriptionContract.ViewPresentable presentable;


    @NonNull
    public static Intent createIntent(@NonNull Context context, String description) {
        Intent intent = new Intent(context, PropertyDescriptionActivity.class);
        intent.putExtra(KEY_DESCRIPTION, description);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_description);
        ButterKnife.bind(this);
        initToolbar();

        presenter = new PropertyDescriptionPresenter(this, NavigatorImpl.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @Override
    public void showError(@NotNull Throwable throwable) {
        handleError(throwable);
    }

    @Override
    public void setPresentable(PropertyDescriptionContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public String getDescriptionFromExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        }
        return extras.getString(KEY_DESCRIPTION);
    }

    @Override
    public void showDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public String getDescription() {
        return description.getText().toString();
    }

    @Override
    public void hideKeyboard() {
        ActivityExtKt.hideKeyboard(this);
    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.autocomplete_address_toolbar);
        toolbar.setNavigationOnClickListener(view -> presentable.onBackClicked());
        toolbar.setOnMenuItemClickListener(item -> {
            if (R.id.action_done == item.getItemId()) {
                presentable.onDoneClicked();
            }
            return false;
        });
    }
}
