package com.soho.sohoapp.feature.marketplaceview.components;

import android.support.annotation.Nullable;
import android.widget.Filter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.soho.sohoapp.Dependencies;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jovan on 25/9/17.
 */
public class PlacesFilter extends Filter {

    private GoogleApiClient googleApiClient;
    private AutocompleteFilter placesFilter;

    public PlacesFilter(GoogleApiClient googleApiClient, AutocompleteFilter placesFilter) {
        this.googleApiClient = googleApiClient;
        this.placesFilter = placesFilter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        ArrayList<AutocompletePrediction> filterData = new ArrayList<>();

        if (charSequence != null) {
            filterData = getAutocomplete(charSequence);
        }

        results.values = filterData;
        if (filterData != null) {
            results.count = filterData.size();
        } else {
            results.count = 0;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        //To override
    }

    @Override
    public CharSequence convertResultToString(Object resultValue) {
        if (resultValue instanceof AutocompletePrediction) {
            return ((AutocompletePrediction) resultValue).getFullText(null);
        } else {
            return super.convertResultToString(resultValue);
        }
    }

    @Nullable
    private ArrayList<AutocompletePrediction> getAutocomplete(CharSequence constraint) {
        if (googleApiClient.isConnected()) {
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi
                            .getAutocompletePredictions(googleApiClient, constraint.toString(),
                                    null, placesFilter);
            AutocompletePredictionBuffer autocompletePredictions = results
                    .await(10, TimeUnit.SECONDS);
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                Dependencies.DEPENDENCIES.getLogger().w(status.getStatusMessage());
                autocompletePredictions.release();
                return null;
            }
            return DataBufferUtils.freezeAndClose(autocompletePredictions);
        }
        return null;
    }
}