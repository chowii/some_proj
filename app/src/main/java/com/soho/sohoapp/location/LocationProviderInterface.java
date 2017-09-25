package com.soho.sohoapp.location;

import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.feature.home.addproperty.data.LatLng;

import io.reactivex.Observable;

public interface LocationProviderInterface {

    Observable<LatLng> getLatLng(String placeId);

    Observable<Location> getAddress(LatLng latLng, String fullAddress);
}
