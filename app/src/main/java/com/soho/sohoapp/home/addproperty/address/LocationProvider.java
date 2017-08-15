package com.soho.sohoapp.home.addproperty.address;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.soho.sohoapp.Dependencies;
import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.logger.Logger;
import com.soho.sohoapp.utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class LocationProvider {
    private static final int ID = 100;
    private final GoogleApiClient googleApiClient;

    private LocationProvider(@NonNull FragmentActivity activity) {
        googleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, ID, connectionResult -> Dependencies.INSTANCE.getLogger().w("Connection failed"))
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    public static LocationProvider newInstance(@NonNull FragmentActivity activity) {
        return new LocationProvider(activity);
    }

    @NonNull
    public Observable<List<PropertyAddress>> getAutocomplete(String constraint) {
        return Observable.fromCallable(() -> {
            Logger logger = Dependencies.INSTANCE.getLogger();

            if (googleApiClient.isConnected()) {
                logger.w("Starting autocomplete query for: " + constraint);

                // Submit the query to the autocomplete API and retrieve a PendingResult that will
                // contain the results when the query completes.

                LatLngBounds BOUNDS_AU = new LatLngBounds(
                        new LatLng(-46.606400, 105.843059), new LatLng(-11.086947, 158.124751));
                PendingResult<AutocompletePredictionBuffer> results =
                        Places.GeoDataApi
                                .getAutocompletePredictions(googleApiClient, constraint, BOUNDS_AU, null);

                // This method should have been called off the main UI thread. Block and wait for at most 60s
                // for a result from the API.
                AutocompletePredictionBuffer autocompletePredictions = results.await(60, TimeUnit.SECONDS);

                // Confirm that the query completed successfully, otherwise return null
                final Status status = autocompletePredictions.getStatus();
                if (!status.isSuccess()) {
                    logger.w("Error getting autocomplete prediction API call: " + status.toString());
                    autocompletePredictions.release();
                    return new ArrayList<AutocompletePrediction>();
                }
                logger.w("Query completed. Received " + autocompletePredictions.getCount() + " predictions.");

                // Freeze the results immutable representation that can be stored safely.
                return DataBufferUtils.freezeAndClose(autocompletePredictions);
            }
            logger.w("Google API client is not connected for autocomplete query.");
            return new ArrayList<AutocompletePrediction>();
        }).map(Converter::toAddressList);
    }

    public Observable<Place> getLocationAddress(String placeId) {
        return Observable.fromCallable(() -> {
            if (googleApiClient.isConnected()) {

                PendingResult<PlaceBuffer> result = Places.GeoDataApi.getPlaceById(googleApiClient, placeId);
                PlaceBuffer places = result.await(60, TimeUnit.SECONDS);
                if (!places.getStatus().isSuccess()) {
                    return null;
                }
                return places.get(0);
            } else {
                return null;
            }
        });
    }
}
