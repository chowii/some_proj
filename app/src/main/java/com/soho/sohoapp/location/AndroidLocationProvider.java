package com.soho.sohoapp.location;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.soho.sohoapp.data.PropertyAddress;
import com.soho.sohoapp.utils.Converter;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class AndroidLocationProvider implements LocationProvider {
    private final GoogleApiClient googleApiClient;

    private AndroidLocationProvider(@NonNull GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    public static AndroidLocationProvider newInstance(@NonNull GoogleApiClient googleApiClient) {
        return new AndroidLocationProvider(googleApiClient);
    }

    @Nullable
    public Observable<PropertyAddress> getLocationAddress(String placeId, String fullAddress) {
        return Observable.fromCallable(() -> {
            if (googleApiClient.isConnected()) {

                PendingResult<PlaceBuffer> result = Places.GeoDataApi.getPlaceById(googleApiClient, placeId);
                PlaceBuffer places = result.await(60, TimeUnit.SECONDS);
                if (!places.getStatus().isSuccess()) {
                    return null;
                }
                Place place = places.get(0);
                return new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
            } else {
                return null;
            }
        }).map(latLng -> Converter.toPropertyAddress(latLng, fullAddress));
    }
}
