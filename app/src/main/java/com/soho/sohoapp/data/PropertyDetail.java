package com.soho.sohoapp.data;

import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyDescribable;
import com.soho.sohoapp.helper.DateHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by chowii on 1/9/17.
 */

public class PropertyDetail extends SohoProperty implements PropertyDescribable {

    private int land_size;
    private String land_size_measurement;
    private String auction_date;
    private Object auction_location;
    private List<String> renovation_details;

    private Date auctionDate;

    @Override
    public int retrieveLandSize() { return land_size; }
    public void applyLandSizeChange(int landSize){
        if (landSize > 0) land_size = landSize;
        else land_size = 0;
    }

    @Override
    public String retrieveLandSizeMeasurement() { return land_size_measurement == null ? "" : land_size_measurement; }

    @Override
    public Date retrieveAuctionDate() {
        if (auction_date == null) return new Date(System.currentTimeMillis());
        auctionDate = DateHelper.stringToDate(auction_date, "yyyy-MM-dd");
        return auctionDate;
    }

    private void applyAuctionDateChange(Date date){
        if (date == null) auctionDate = new Date(System.currentTimeMillis());
        else auctionDate = date;
    }

    public String retrieveDisplayableAuctionDate() {
        if (auctionDate == null) auctionDate = retrieveAuctionDate();
        Calendar c = Calendar.getInstance();
        c.setTime(auctionDate);
        String displayableDate = c.get(Calendar.DAY_OF_MONTH) + " / " + c.get(Calendar.MONTH) + " / " + c.get(Calendar.YEAR);
        return  displayableDate;
    }

    @Override
    public Object retrieveAuctionLocation() {
        return auction_location == null ? "" : auction_location;
    }

    @Override
    public List<String> retrieveRenovationDetails() {
        return renovation_details == null ? new ArrayList<>() : renovation_details;
    }



}
