package com.soho.sohoapp.network.results;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PropertyResult {
    @SerializedName("id")
    public int id;

    @SerializedName("state")
    public String state;

    @SerializedName("title")
    public String title;

    @SerializedName("type_of_property")
    public String type;

    @SerializedName("bedrooms")
    public int bedrooms;

    @SerializedName("bathrooms")
    public int bathrooms;

    @SerializedName("carspots")
    public int carspots;

    @SerializedName("location")
    public Location location;

    @SerializedName("property_listing")
    public PropertyListing propertyListing;

    @SerializedName("photos")
    public List<Photo> photos;

    @SerializedName("verifications")
    public List<Verification> verifications;

    public static class Photo {
        @SerializedName("image")
        public Image image;
    }

    public static class Image {
        @SerializedName("url")
        public String url;
    }

    public static class Location {
        @SerializedName("address_1")
        public String address_1;

        @SerializedName("address_2")
        public String address_2;
    }

    public static class PropertyListing {
        @SerializedName("id")
        public int id;

        @SerializedName("state")
        public String state;
    }

    public static class Verification {
        @SerializedName("id")
        public int id;

        @SerializedName("type")
        public String type;

        @SerializedName("text")
        public String text;

        @SerializedName("state")
        public String state;
    }

}
