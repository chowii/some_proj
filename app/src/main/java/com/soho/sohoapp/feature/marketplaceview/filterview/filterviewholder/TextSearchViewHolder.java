package com.soho.sohoapp.feature.marketplaceview.filterview.filterviewholder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Places;
import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.feature.marketplaceview.filterview.fitlermodel.FilterSearchItem;
import com.soho.sohoapp.home.BaseFormModel;
import com.soho.sohoapp.home.addproperty.address.AddressContract;
import com.soho.sohoapp.home.addproperty.address.AddressPresenter;
import com.soho.sohoapp.home.addproperty.address.PlaceAutocompleteAdapter;
import com.soho.sohoapp.location.AndroidLocationProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 18/8/17.
 */

public class TextSearchViewHolder extends BaseFormViewHolder<FilterSearchItem>
        implements AddressContract.View,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks
{

    private final View view;

    GoogleApiClient apiClient;
    private final AddressPresenter presenter;
    private AddressContract.ViewActionsListener actionsListener;
    List<String> suburbList;

    @BindView(R.id.suburb_auto_complete)
    AutoCompleteTextView suburbEditText;

    public TextSearchViewHolder(View itemView) {
        super(itemView);
        view = itemView;
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

        suburbEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutocompletePrediction item = adapter.getItem(position);
                if(item != null){
                    actionsListener.onAddressClicked(item.getPlaceId(), item.getPrimaryText(null).toString());

                    String suburbText = item.getPrimaryText(null).toString();
                    if(!suburbList.contains(suburbText)) suburbList.add(suburbText);


                    adapter.addToList(suburbText);
                    StringBuilder builder = new StringBuilder();

                    for(String suburb: suburbList)
                        builder.append(suburb + ", ");
                    suburbEditText.setText(builder.toString());
                    suburbEditText.setSelection(builder.length());
                }

            }
        });

        suburbEditText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                String[] text = string.split(",+");
                for(int i = 0; i < text.length; i++){
                    if(suburbList.size() > 0){
                        if(!suburbList.get(i).equalsIgnoreCase(text[i])){
                            suburbList.set(i, text[i]);
                        }
                    }
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actionsListener.onAddressChanged(s.subSequence(start, s.length()).toString());
            }
        });
    }

    @Override
    public void setActionsListener(AddressContract.ViewActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void hideLoadingDialog() {

    }

    @Override
    public void showError(Throwable t) {

    }

    @Override
    public void setAddress(String s) {

    }

    @Override
    public void showEmptyLocationError() {

    }

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
}
