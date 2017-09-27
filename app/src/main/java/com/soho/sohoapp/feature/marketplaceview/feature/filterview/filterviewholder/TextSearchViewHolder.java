package com.soho.sohoapp.feature.marketplaceview.feature.filterview.filterviewholder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Places;
import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.customviews.TokenizedSuburbAutoCompleteTextView;
import com.soho.sohoapp.database.entities.Suburb;
import com.soho.sohoapp.feature.home.addproperty.address.PlaceAutocompleteAdapter;
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.FilterSearchItem;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

/**
 * Created by chowii on 18/8/17.
 */

public class TextSearchViewHolder extends BaseFormViewHolder<FilterSearchItem>
        implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        TokenCompleteTextView.TokenListener<Suburb>
{

    private final View view;

    private GoogleApiClient apiClient;
    private List<String> suburbList;

    @BindView(R.id.suburb_auto_complete)
    TokenizedSuburbAutoCompleteTextView suburbEditText;

    public TextSearchViewHolder(View itemView, OnViewHolderItemValueChangeListener listener) {
        super(itemView);
        view = itemView;
        updatedListener = listener;
        suburbList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(FilterSearchItem model) {
        updatedListener.onChange("by_google_places[place_ids]", suburbList);
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()){
            new AlertDialog.Builder(view.getContext())
                    .setMessage(connectionResult.getErrorMessage())
                    .show();
            DEPENDENCIES.getLogger().d("onConnectionFailed: " + connectionResult.getErrorMessage());
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) { }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onTokenAdded(Suburb token) {
        suburbList.add(token.getPlaceId());
        updatedListener.onChange("by_google_places[place_ids]", suburbList);
    }

    @Override
    public void onTokenRemoved(Suburb token) {
        suburbList.remove(token.getPlaceId());
        updatedListener.onChange("by_google_places[place_ids]", suburbList);
    }
}
