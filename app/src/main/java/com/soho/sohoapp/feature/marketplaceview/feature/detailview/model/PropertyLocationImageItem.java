package com.soho.sohoapp.feature.marketplaceview.feature.detailview.model;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.soho.sohoapp.R;
import com.soho.sohoapp.SohoApplication;
import com.soho.sohoapp.feature.home.BaseModel;

import java.util.ArrayList;


/**
 * Created by chowii on 6/9/17.
 */

public class PropertyLocationImageItem implements BaseModel {


    @Override
    public int getItemViewType() { return R.layout.item_property_image; }

    public static final String STATIC_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?size=1200x600&zoom=14";
    private final String PARAM_MARKERS_COLOR = "&markers=color:green%7C";

    private final LatLng latLng;
    private StringBuilder imageUrl = new StringBuilder(STATIC_MAP_URL);

    private boolean isMasked;

    public PropertyLocationImageItem(LatLng latLng, boolean isMasked) {
        this.latLng = latLng;
        if (!isMasked) imageUrl.append(PARAM_MARKERS_COLOR);
        imageUrl.append(latLng.latitude)
                .append(",")
                .append(latLng.longitude);
        this.isMasked = isMasked;
    }
    public PropertyLocationImageItem(double lat, double lng, boolean isMasked) {
        latLng = new LatLng(lat, lng);
        if (!isMasked) imageUrl.append(PARAM_MARKERS_COLOR);
        imageUrl.append(latLng.latitude)
                .append(",")
                .append(latLng.longitude);
        this.isMasked = isMasked;
    }

    public PropertyLocationImageItem(LatLng latLng, String url, boolean isMasked) {
        imageUrl = new StringBuilder(url);
        this.latLng = latLng;
        this.isMasked = isMasked;
    }

    public PropertyLocationImageItem(double lat, double lng, String url, boolean isMasked) {
        imageUrl = new StringBuilder(url);
        latLng = new LatLng(lat, lng);
        this.isMasked = isMasked;
    }

    public void applyImageUrlChange(String imageUrl){
        this.imageUrl = imageUrl == null ? new StringBuilder(STATIC_MAP_URL) : new StringBuilder(imageUrl) ;
    }

    public String retrieveImageUrl(){
        if (isMasked) return retrieveImageUrlWithMask(retrieveLatLng());
        return imageUrl.toString();
    }

    public LatLng retrieveLatLng() { return latLng; }

    public boolean isMasked(){ return isMasked; }

    public String retrieveImageUrlWithMask(LatLng latLng){
        ArrayList<LatLng> circlePoints = getCircleAsPolyline(latLng, 100.0);
        String colorPrimary = SohoApplication.getContext().getString(R.color.colorPrimary).replaceFirst("#ff","");
        String darkerPrimary = SohoApplication.getContext().getString(R.color.darkerPrimary).replaceFirst("#ff","");

        if (circlePoints.size() > 0)
            imageUrl.append("&path=color:0x")               // Path Color parameter in hex; prefix `0x` is included
                    .append(darkerPrimary)                  // Path Color from color res
                    .append("%7Cweight:1%7Cfillcolor:0x")   // Fill color of the circle in hex; prefix `0x` is included
                    .append(colorPrimary)                   // Fill Color from color res
                    .append("%7Cenc:")                      // Path location parameter
                    .append(PolyUtil.encode(circlePoints)); // Path location value

        return imageUrl.toString();
    }

    private ArrayList<LatLng> getCircleAsPolyline(LatLng latlng, @Nullable Double radius) {
        final double EARTH_RADIUS_KM = 6371;
        final double TO_RADIAN = Math.PI / 180.0;
        ArrayList<LatLng> path = new ArrayList<>();

        double latitudeRadians = latlng.latitude * TO_RADIAN;
        double longitudeRadians = latlng.longitude * TO_RADIAN;
        double radiusRadians = (radius == null ? 200 : radius) / 1000.0 / EARTH_RADIUS_KM;

        double calcLatPrefix = Math.sin(latitudeRadians) * Math.cos(radiusRadians);
        double calcLatSuffix = Math.cos(latitudeRadians) * Math.sin(radiusRadians);

        for (int angle = 0; angle < 361; angle += 10) {
            double angleRadians = angle * TO_RADIAN;

            double latitude = Math.asin(calcLatPrefix + calcLatSuffix * Math.cos(angleRadians));
            double longitude = ((longitudeRadians + Math.atan2(
                                    Math.sin(angleRadians) * Math.sin(radiusRadians) * Math.cos(latitudeRadians),
                                    Math.cos(radiusRadians) - Math.sin(latitudeRadians) * Math.sin(latitude))
                                ) * 180) / Math.PI;
            latitude = latitude * 180.0 / Math.PI;
            path.add(new LatLng(latitude, longitude));
        }
        return path;
    }

}
