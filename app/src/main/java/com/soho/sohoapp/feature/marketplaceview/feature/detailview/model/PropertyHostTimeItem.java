package com.soho.sohoapp.feature.marketplaceview.feature.detailview.model;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.models.InspectionTime;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.feature.home.BaseModel;

/**
 * Created by chowii on 4/9/17.
 */

public class PropertyHostTimeItem implements BaseModel {

    @Override
    public int getItemViewType() { return R.layout.item_property_date_information; }

    public PropertyHostTimeItem(InspectionTime inspectionTime, Location location, String state, boolean isAppointmentOnly) {
        propertyInspectionItem = new PropertyInspectionItem(inspectionTime, isAppointmentOnly);
        this.location = location;
        this.state = state;
    }

    public PropertyHostTimeItem(Long date, Location location, boolean isOnSite, String state) {
        propertyAuctionItem = new PropertyAuctionItem(date, isOnSite);
        this.location = location;
        this.state = state;
    }

    private PropertyInspectionItem propertyInspectionItem;
    private PropertyAuctionItem propertyAuctionItem;
    private String state;
    private Location location;

    public PropertyInspectionItem retrievePropertyInspectionItem(){ return propertyInspectionItem; }

    public PropertyAuctionItem retrievePropertyAuctionItem(){ return propertyAuctionItem; }

    public Location retrieveLocation() { return location; }

    public String retrieveState() { return state; }

    public void applyStateChange(String state) { this.state = state; }

    public static class PropertyInspectionItem {

        private final boolean isAppointmentOnly;
        private InspectionTime inspectionTime;

        PropertyInspectionItem(InspectionTime inspectionTime, boolean isAppointmentOnly) {
            this.inspectionTime = inspectionTime;
            this.isAppointmentOnly = isAppointmentOnly;
        }

        public InspectionTime getPropertyInspectionTime() { return inspectionTime; }

        public boolean isAppointmentOnly() { return isAppointmentOnly; }
    }


    public static class PropertyAuctionItem {

        private Long auctionDate;
        private Location location;
        private boolean isOnSite;

        PropertyAuctionItem(Long inspectionTime, boolean isOnSite) {
            this.auctionDate = inspectionTime;
            this.isOnSite = isOnSite;
        }

        public Long getAuctionDate() {
            return auctionDate;
        }

        public void setAuctionDate(Long auctionDate) {
            this.auctionDate = auctionDate;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public boolean isOnSite() {
            return isOnSite;
        }

        public void setisOnSite(boolean mIsOnSite) {
            this.isOnSite = isOnSite;
        }
    }
}
