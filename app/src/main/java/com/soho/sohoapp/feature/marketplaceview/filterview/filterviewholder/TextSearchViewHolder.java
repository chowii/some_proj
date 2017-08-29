package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Places;
import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.customviews.TokenizedSuburbAutoCompleteTextView;
import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.FilterSearchItem;
import com.soho.sohoapp.home.addproperty.address.AddressContract;
import com.soho.sohoapp.home.addproperty.address.AddressPresenter;
import com.soho.sohoapp.home.addproperty.address.PlaceAutocompleteAdapter;
import com.soho.sohoapp.location.AndroidLocationProvider;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chowii on 18/8/17.
 */

public class TextSearchViewHolder extends BaseFormViewHolder<FilterSearchItem>
        implements AddressContract.View,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        TokenCompleteTextView.TokenListener<AutocompletePrediction>
{

    private final View view;

    GoogleApiClient apiClient;
    private final AddressPresenter presenter;
    private AddressContract.ViewActionsListener actionsListener;
    List<String> suburbList;

    @BindView(R.id.suburb_auto_complete)
    TokenizedSuburbAutoCompleteTextView suburbEditText;

    public TextSearchViewHolder(View itemView, OnViewHolderItemValueChangeListener listener) {
        super(itemView);
        view = itemView;
        updatedListener = listener;
        suburbList = new ArrayList();
        presenter = new AddressPresenter(this, AndroidLocationProvider.newInstance(apiClient));
    }

    @Override
    public void onBindViewHolder(FilterSearchItem model) {
        presenter.startPresenting();
        apiClient = new GoogleApiClient.Builder(view.getContext())
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        apiClient.connect();
        PlaceAutocompleteAdapter adapter = new PlaceAutocompleteAdapter(view.getContext(), apiClient, null);
        suburbEditText.setAdapter(adapter);
        suburbEditText.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);
        suburbEditText.setTokenListener(this);
    }

    @Override
    public void setActionsListener(AddressContract.ViewActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    @Override
    public void showLoadingDialog() { }

    @Override
    public void hideLoadingDialog() { }

    @Override
    public void showError(Throwable t) { }

    @Override
    public void setAddress(String s) { }

    @Override
    public void showEmptyLocationError() { }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public void sendAddressToActivity(PropertyAddress address) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()){
            new AlertDialog.Builder(view.getContext())
                    .setMessage(connectionResult.getErrorMessage())
                    .show();
            Log.d("LOG_TAG---", "onConnectionFailed: " + connectionResult.getErrorMessage());
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) { }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onTokenAdded(AutocompletePrediction token) {
        suburbList.add(token.getPlaceId());
        updatedListener.onChange("by_google_places[place_ids]", suburbList);
    }

    @Override
    public void onTokenRemoved(AutocompletePrediction token) {
        suburbList.remove(token.getPlaceId());
        updatedListener.onChange("by_google_places[place_ids]", suburbList);
    }
}
