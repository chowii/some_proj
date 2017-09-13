package com.soho.sohoapp.feature.marketplaceview.feature.detailview.model;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.PropertyInspectionTime;
import com.soho.sohoapp.data.PropertyLocation;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.helper.DateHelper;

import java.util.Calendar;

/**
 * Created by chowii on 4/9/17.
 */

public class PropertyHostTimeItem implements BaseModel {

    @Override
    public int getItemViewType() { return R.layout.item_property_date_information; }

    public PropertyHostTimeItem(PropertyInspectionTime inspectionTime, PropertyLocation location, String state, boolean isAppointmentOnly) {
        propertyInspectionItem = new PropertyInspectionItem(inspectionTime, isAppointmentOnly);
        this.location = location;
        this.state = state;
    }

    public PropertyHostTimeItem(String date, PropertyLocation location, boolean isOnSite, String state) {
        propertyAuctionItem = new PropertyAuctionItem(date, isOnSite);
        this.location = location;
        this.state = state;
    }

    private PropertyInspectionItem propertyInspectionItem;
    private PropertyAuctionItem propertyAuctionItem;
    private String state;
    private PropertyLocation location;

    public PropertyInspectionItem retrievePropertyInspectionItem(){ return propertyInspectionItem; }

    public PropertyAuctionItem retrievePropertyAuctionItem(){ return propertyAuctionItem; }

    public PropertyLocation retrieveLocation() { return location; }

    public String retrieveState() { return state; }

    public void applyStateChange(String state) { this.state = state; }

    public static class PropertyInspectionItem {

        private final boolean isAppointmentOnly;
        private PropertyInspectionTime inspectionTime;

        PropertyInspectionItem(PropertyInspectionTime inspectionTime, boolean isAppointmentOnly) {
            this.inspectionTime = inspectionTime;
            this.isAppointmentOnly = isAppointmentOnly;
        }

        public PropertyInspectionTime getPropertyInspectionTime() { return inspectionTime; }

        public boolean isAppointmentOnly() { return isAppointmentOnly; }
    }


    public static class PropertyAuctionItem {

        private String auctionDate;
        private PropertyLocation location;
        private boolean mIsOnSite;

        PropertyAuctionItem(String inspectionTime, boolean mIsOnSite) {
            this.auctionDate = inspectionTime;
            this.location = location;
            this.mIsOnSite = mIsOnSite;
        }

        public Calendar retrieveAuctionDate() { return DateHelper.retrieveCalendarFromApiDate(auctionDate); }

        public void applyDateChange(String date) { this.auctionDate = date; }

        public boolean isOnSite(){ return mIsOnSite; }

        public void applyIsOnSiteChange(boolean isOnSite){ mIsOnSite = isOnSite; }

    }
}
