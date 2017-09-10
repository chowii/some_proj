package com.soho.sohoapp.feature.marketplaceview.feature.detailview;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.SohoApplication;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyHostTimeItem;
import com.soho.sohoapp.helper.DateHelper;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by chowii on 4/9/17.
 */

class PropertyDetailAuctionViewHolder extends BaseViewHolder<PropertyHostTimeItem> {

    @BindView(R.id.description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.auction_location_text_view)
    TextView auctionLocationTextView;

    @BindView(R.id.add_to_calendar_button)
    TextView addToCalendarButton;

    OnAddToCalenderClickListener listener;

    PropertyDetailAuctionViewHolder(View itemView, OnAddToCalenderClickListener listener) {
        super(itemView);
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(PropertyHostTimeItem model) {
        if (model.retrievePropertyAuctionItem() != null){
            configureAuctionTime(model);
            toggleVisibility(VISIBLE, VISIBLE, VISIBLE);
        }else if (model.retrievePropertyInspectionItem().isAppointmentOnly()){
            addToCalendarButton.setText("");
            descriptionTextView.setText(R.string.inspection_times);

            toggleVisibility(GONE, GONE, VISIBLE);
        }else {
            configureInspectionTime(model.retrievePropertyInspectionItem());
            toggleVisibility(GONE, VISIBLE, VISIBLE);
        }
        addToCalendarButton.setOnClickListener(v ->
                listener.onAddToCalenderClicked(model, model.retrieveState().equalsIgnoreCase("Auction") ? model.retrieveState() : "Inspection"));
    }

    private void configureAuctionTime(PropertyHostTimeItem model) {
        PropertyHostTimeItem.PropertyAuctionItem propertyAuctionItem = model.retrievePropertyAuctionItem();
        String auctionDate = DateHelper.retrieveShortDisplayableDate(propertyAuctionItem.retrieveAuctionDate());
        String auctionTime = DateHelper.retrieveShortDisplayableTime(propertyAuctionItem.retrieveAuctionDate());
        descriptionTextView.setText(auctionDate);
        addToCalendarButton.setText(auctionTime);

        StringBuilder auctionLocationText = new StringBuilder(SohoApplication.getContext().getString(R.string.auction_location_string));
        auctionLocationText.append(propertyAuctionItem.isOnSite() ? "On site" : model.retrieveLocation().retrieveFullAddress());
        auctionLocationTextView.setText(auctionLocationText.toString());
    }

    private void configureInspectionTime(PropertyHostTimeItem.PropertyInspectionItem inspectionTime) {
        StringBuilder timeBuilder = new StringBuilder();
        timeBuilder.append(inspectionTime.getPropertyInspectionTime().retrieveDisplayableStartTime());
        timeBuilder.append(" - ");
        timeBuilder.append(inspectionTime.getPropertyInspectionTime().retrieveDisplayableEndTime());
        addToCalendarButton.setText(timeBuilder.toString());
        descriptionTextView.setText(inspectionTime.getPropertyInspectionTime().retrieveDisplayableStartDate());
    }

    private void toggleVisibility(int auctionLocationVisibility, int addToCalendarVisibility, int descriptionVisibility) {
        auctionLocationTextView.setVisibility(auctionLocationVisibility);
        addToCalendarButton.setVisibility(addToCalendarVisibility);
        descriptionTextView.setVisibility(descriptionVisibility);
    }

    interface OnAddToCalenderClickListener {
        void onAddToCalenderClicked(PropertyHostTimeItem timeItem, String state);
    }

}
