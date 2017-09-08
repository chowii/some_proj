package com.soho.sohoapp.feature.marketplaceview.feature.detailview;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
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
        if (model.propertyAuctionItem != null){
            String auctionDate = DateHelper.retrieveShortDisplayableDate(model.propertyAuctionItem.retrieveAuctionDate());
            String auctionTime = DateHelper.retrieveShortDisplayableTime(model.propertyAuctionItem.retrieveAuctionDate());
            descriptionTextView.setText(auctionDate);
            addToCalendarButton.setText(auctionTime);
            auctionLocationTextView.setText(model.propertyAuctionItem.isOnSite() ? "On site" : model.propertyAuctionItem.getLocation().retrieveFullAddress());
        }else if (model.propertyInspectionItem.isAppointmentOnly()){
            addToCalendarButton.setText("");
            descriptionTextView.setText(R.string.inspection_times);

            auctionLocationTextView.setVisibility(GONE);
            addToCalendarButton.setVisibility(GONE);
            descriptionTextView.setVisibility(VISIBLE);

        }else {
            PropertyHostTimeItem.PropertyInspectionItem inspectionTime = model.propertyInspectionItem;
            StringBuilder timeBuilder = new StringBuilder();
            timeBuilder.append(inspectionTime.getPropertyInspectionTime().retrieveDisplayableStartTime());
            timeBuilder.append(" - ");
            timeBuilder.append(inspectionTime.getPropertyInspectionTime().retrieveDisplayableEndTime());
            addToCalendarButton.setText(timeBuilder.toString());
            descriptionTextView.setText(inspectionTime.getPropertyInspectionTime().retrieveDisplayableStartDate());

            auctionLocationTextView.setVisibility(GONE);
            addToCalendarButton.setVisibility(VISIBLE);
            descriptionTextView.setVisibility(VISIBLE);

        }
        addToCalendarButton.setOnClickListener(v -> listener.onAddToCalenderClicked(model, model.propertyAuctionItem.retrieveState().equalsIgnoreCase("auction") ? model.propertyAuctionItem.retrieveState() : "inspection"));
    }

    interface OnAddToCalenderClickListener {
        void onAddToCalenderClicked(PropertyHostTimeItem timeItem, String state);
    }

}
