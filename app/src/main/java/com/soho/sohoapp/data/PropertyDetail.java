package com.soho.sohoapp.data;

import com.soho.sohoapp.R;
import com.soho.sohoapp.SohoApplication;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyDescribable;
import com.soho.sohoapp.helper.DateHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by chowii on 1/9/17.
 */

public class PropertyDetail extends SohoProperty implements PropertyDescribable {

    private int land_size;
    private String land_size_measurement;
    private String auction_date;
    private Object auction_location;
    private List<String> renovation_details;

    private Calendar auctionDate;

    @Override
    public int retrieveLandSize() { return land_size; }
    public void applyLandSizeChange(int landSize){
        if (landSize > 0) land_size = landSize;
        else land_size = 0;
    }

    @Override
    public String retrieveLandSizeMeasurement() { return land_size_measurement == null ? "" : land_size_measurement; }

    @Override
    public Calendar retrieveAuctionDate() {
        if (auction_date == null) return Calendar.getInstance();
        auctionDate = DateHelper.stringToCalendar(auction_date, DateHelper.DATE_FORMAT_ddMMyyyy);
        return auctionDate;
    }

    private void applyAuctionDateChange(Calendar date){
        if (date == null) auctionDate = Calendar.getInstance();
        else auctionDate = date;
    }

    public String retrieveDisplayableAuctionDate() {
        if (auctionDate == null) auctionDate = retrieveAuctionDate();
        return String.format(
                Locale.getDefault(),
                SohoApplication.getStringFromResource(R.string.date_backslash_format_string),
                auctionDate.get(Calendar.DAY_OF_MONTH),
                auctionDate.get(Calendar.MONTH),
                auctionDate.get(Calendar.YEAR)
        );
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
