package com.soho.sohoapp.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.feature.home.addproperty.data.LatLng;
import com.soho.sohoapp.utils.Converter;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class LocationProviderImpl implements LocationProviderInterface {
    private final GoogleApiClient googleApiClient;
    private final Context context;

    private LocationProviderImpl(@NonNull GoogleApiClient googleApiClient, @NonNull Context context) {
        this.googleApiClient = googleApiClient;
        this.context = context;
    }

    public static LocationProviderImpl newInstance(@NonNull Context context, @NonNull GoogleApiClient googleApiClient) {
        return new LocationProviderImpl(googleApiClient, context);
    }

    @Nullable
    public Observable<LatLng> getLatLng(String placeId) {
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
        });
    }

    @Override
    public Observable<Location> getAddress(LatLng latLng, String fullAddress) {
        return Observable.fromCallable(() -> {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());
            addresses = geocoder.getFromLocation(latLng.getLat(), latLng.getLng(), 1);
            return addresses.get(0);
        }).map(address -> Converter.toPropertyAddress(address, fullAddress));
    }
}
