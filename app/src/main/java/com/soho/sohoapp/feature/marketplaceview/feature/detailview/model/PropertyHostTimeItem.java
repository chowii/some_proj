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

    public PropertyHostTimeItem(PropertyInspectionTime date, String state, boolean isAppointmentOnly) {
        propertyInspectionItem = new PropertyInspectionItem(date, isAppointmentOnly);
    }

    public PropertyHostTimeItem(String date, PropertyLocation location, boolean isOnSite, String state) {
        propertyAuctionItem = new PropertyAuctionItem(location, date, state, isOnSite);
    }

    public PropertyInspectionItem propertyInspectionItem;
    public PropertyAuctionItem propertyAuctionItem;

    public static class PropertyInspectionItem {

        private final boolean isAppointmentOnly;
        private PropertyInspectionTime inspectionTime;

        public PropertyInspectionItem(PropertyInspectionTime inspectionTime, boolean isAppointmentOnly) {
            this.inspectionTime = inspectionTime;
            this.isAppointmentOnly = isAppointmentOnly;
        }

        public PropertyInspectionTime getPropertyInspectionTime() { return inspectionTime; }

        public boolean isAppointmentOnly() { return isAppointmentOnly; }
    }


    public static class PropertyAuctionItem {

        private String auctionDate;
        private String state;
        private PropertyLocation location;
        private boolean mIsOnSite;

        public PropertyAuctionItem(PropertyLocation location, String auctionDate, String state, boolean mIsOnSite) {
            this.auctionDate = auctionDate;
            this.state = state;
            this.location = location;
            this.mIsOnSite = mIsOnSite;
        }

        public Calendar retrieveAuctionDate() {
            Calendar auctionCalendar = Calendar.getInstance();
            auctionCalendar.setTime(DateHelper.stringToDate(auctionDate == null ? "" : auctionDate, "yyyy-MM-dd"));
            return auctionCalendar;
        }

        public void applyDateChange(String date) { this.auctionDate = date; }

        public PropertyLocation getLocation() { return location; }

        public boolean isOnSite(){ return mIsOnSite; }

        public void applyIsOnSiteChange(boolean isOnSite){ mIsOnSite = isOnSite; }

        public String retrieveState() { return state; }

        public void applyStateChange(String state) { this.state = state; }

    }
}
