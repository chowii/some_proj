package com.soho.sohoapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chowii on 5/9/17.
 */

public class PropertyListing {

    private int id;
    private String state;

    @SerializedName("receive_sales_offers")
    private boolean hasReceiveSalesOffers;

    @SerializedName("receive_rent_offers")
    private boolean hasReceiveRentOffers;

    @SerializedName("sale_title")
    private String saleTitle;

    @SerializedName("rent_title")
    private String rentTitle;

    @SerializedName("auction_title")
    private String auctionTitle;

    @SerializedName("discoverable_title")
    private String discoverableTitle;

    @SerializedName("on_site_auction")
    private boolean isOnSiteAuction;

    @SerializedName("auction_date")
    private String auctionDate;

    @SerializedName("auction_time")
    private String auctionTime;

    @SerializedName("rent_payment_frequency")
    private String rentPaymentFrequency;

    @SerializedName("available_from")
    private String availableFrom;

    @SerializedName("appointment_only")
    private boolean isAppointmentOnly;

    @SerializedName("off_site_location")
    private Object offSiteLocation;

    @SerializedName("inspection_times")
    private List<PropertyInspectionTime> inspectionTimes;

    public int retrieveId() { return id; }

    public String retrieveState() { return state == null ? "" : state; }

    public void applyStateChange(String state) { this.state = state == null ? "" : state; }

    public boolean isHasReceiveSalesOffers() { return hasReceiveSalesOffers; }

    public void applyHasReceiveSalesOffersChange(boolean hasReceiveSalesOffers) {
        this.hasReceiveSalesOffers = hasReceiveSalesOffers;
    }

    public boolean isHasReceiveRentOffers() { return hasReceiveRentOffers; }

    public void applyHasReceiveRentOffersChange(boolean hasReceiveRentOffers) {
        this.hasReceiveRentOffers = hasReceiveRentOffers;
    }

    public String retrieveSaleTitle() { return saleTitle == null ? "" : saleTitle; }

    public void applySaleTitleChange(String saleTitle) {
        this.saleTitle = saleTitle == null ? "" : saleTitle;
    }

    public String retrieveRentTitle() { return rentTitle == null ? "" : rentTitle; }

    public void applyRentTitleChange(String rentTitle) {
        if (rentTitle == null) this.rentTitle = "";
        else this.rentTitle = rentTitle;
    }

    public String retrieveAuctionTitle() { return auctionTitle == null ? "" : auctionTitle; }

    public void applyAuctionTitleChange(String auctionTitle) {
       this.auctionTitle = auctionTitle == null ? "" : auctionTitle;
    }

    // retrieve

    public String retrieveDiscoverableTitle() {
        return discoverableTitle == null ? "" : discoverableTitle;
    }

    public void applyDiscoverableTitleChange(String discoverableTitle) {
        this.discoverableTitle = discoverableTitle == null ? "" : discoverableTitle;
    }

    public boolean isOnSiteAuction() { return isOnSiteAuction; }

    public void applyOnSiteAuctionChange(boolean onSiteAuction) { isOnSiteAuction = onSiteAuction; }

    public String retrieveAuctionDate() { return auctionDate; }

    public void setAuctionDate(String auctionDate) { this.auctionDate = auctionDate == null ? "" : auctionDate; }

    public String retrieveRentPaymentFrequency() { return rentPaymentFrequency; }

    public void applyRentPaymentFrequencyChange(String rentPaymentFrequency) {
        this.rentPaymentFrequency = rentPaymentFrequency == null ? "" : rentPaymentFrequency;
    }

    public boolean isAppointmentOnly() { return isAppointmentOnly; }

    public void applyAppointmentOnlyChange(boolean appointmentOnly) { isAppointmentOnly = appointmentOnly; }

    public List<PropertyInspectionTime> retrieveInspectionTimes() {
        return inspectionTimes == null ? new ArrayList() : inspectionTimes;
    }
}
