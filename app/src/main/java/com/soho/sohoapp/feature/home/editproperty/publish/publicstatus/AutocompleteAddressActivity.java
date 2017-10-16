package com.soho.sohoapp.feature.home.editproperty.publish.publicstatus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Places;
import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.dialogs.LoadingDialog;
import com.soho.sohoapp.extensions.ActivityExtKt;
import com.soho.sohoapp.feature.home.addproperty.address.PlaceAutocompleteAdapter;
import com.soho.sohoapp.location.LocationProviderImpl;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.utils.TextWatcherAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class AutocompleteAddressActivity extends AbsActivity implements AutocompleteAddressContract.ViewInteractable {
    private static final int GOOGLE_CLIENT_ID = 1000;
    public static final String KEY_SHOW_CONFIRMATION_DIALOG = "KEY_SHOW_CONFIRMATION_DIALOG";
    private AutocompleteAddressContract.ViewPresentable presentable;
    private AutocompleteAddressPresenter presenter;
    private GoogleApiClient googleApiClient;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.autocomplete)
    AutoCompleteTextView autocomplete;
    private LoadingDialog loadingDialog;

    @NonNull
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, AutocompleteAddressActivity.class);
    }

    @NonNull
    public static Intent createIntent(@NonNull Context context, boolean showConfirmationDialog) {
        Intent intent = new Intent(context, AutocompleteAddressActivity.class);
        intent.putExtra(KEY_SHOW_CONFIRMATION_DIALOG, showConfirmationDialog);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete_address);
        ButterKnife.bind(this);

        initToolbar();
        initAutocomplete();

        presenter = new AutocompleteAddressPresenter(this,
                NavigatorImpl.newInstance(this),
                LocationProviderImpl.newInstance(this, googleApiClient));
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

    @Override
    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show(getString(R.string.common_loading));
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public void setAddress(String s) {
        autocomplete.setText(s);
    }

    @Override
    public void showLoadingError() {
        showToast(R.string.common_loading_error);
    }

    @OnClick(R.id.clearAddress)
    void clearAddress() {
        presentable.onClearClicked();
    }

    @Override
    public void showKeyboard() {
        autocomplete.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    @Override
    public void hideKeyboard() {
        ActivityExtKt.hideKeyboard(this);
    }

    @Override
    public void showEmptyLocationError() {
        showToast(R.string.autocomplete_address_not_selected);
    }

    @Override
    public boolean confirmationDialogIsNeeded() {
        Bundle extras = getIntent().getExtras();
        return extras != null && extras.getBoolean(KEY_SHOW_CONFIRMATION_DIALOG);
    }

    @Override
    public void showConfirmationDialog(DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this).setMessage(R.string.autocomplete_address_confirmation_message)
                .setTitle(R.string.autocomplete_address_confirmation)
                .setPositiveButton(R.string.autocomplete_address_confirmation_yes, listener)
                .setNegativeButton(R.string.autocomplete_address_confirmation_no, listener)
                .show();
    }

    private void initAutocomplete() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, GOOGLE_CLIENT_ID, connectionResult ->
                        DEPENDENCIES.getLogger().w("Connection failed"))
                .addApi(Places.GEO_DATA_API)
                .build();
        PlaceAutocompleteAdapter adapter = new PlaceAutocompleteAdapter(this, googleApiClient, null);
        autocomplete.setOnItemClickListener((parent, view, position, id) -> {
            AutocompletePrediction item = adapter.getItem(position);
            if (item != null) {
                presentable.onAddressClicked(item.getPlaceId(), item.getFullText(null).toString());
            }
        });
        autocomplete.setAdapter(adapter);
        autocomplete.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presentable.onAddressChanged(charSequence.toString());
            }
        });
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
