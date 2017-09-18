package com.soho.sohoapp.feature.marketplaceview.feature.detailview;

import android.view.View;
import android.widget.TextView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.extensions.LongExtKt;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyHostTimeItem;
import com.soho.sohoapp.helper.DateHelper;

import java.util.Locale;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.soho.sohoapp.SohoApplication.getStringFromResource;

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

    private OnAddToCalenderClickListener listener;

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
                listener.onAddToCalenderClicked(model,
                                    model.retrieveState().equalsIgnoreCase(getStringFromResource(R.string.property_state_auction)) ?
                                            model.retrieveState() :
                                            getStringFromResource(R.string.property_state_inspection)));
    }

    private void configureAuctionTime(PropertyHostTimeItem model) {
        PropertyHostTimeItem.PropertyAuctionItem propertyAuctionItem = model.retrievePropertyAuctionItem();
        descriptionTextView.setText(LongExtKt.toStringWithDisplayFormat(propertyAuctionItem.getAuctionDate()));
        addToCalendarButton.setText(LongExtKt.toStringWithTimeFormat(propertyAuctionItem.getAuctionDate()));

        auctionLocationTextView.setText(String.format(
                                    Locale.getDefault(),
                                    getStringFromResource(R.string.property_detail_auction_location_format_string),
                                    getStringFromResource(R.string.auction_location_string),
                                    propertyAuctionItem.isOnSite() ?
                                    "On site" :
                                    model.retrieveLocation().getFullAddress()));
    }

    private void configureInspectionTime(PropertyHostTimeItem.PropertyInspectionItem inspectionTime) {
        addToCalendarButton.setText(String.format(
                                    Locale.getDefault(),
                                    getStringFromResource(R.string.property_detail_property_viewing_time_fromat_string),
                                    LongExtKt.toStringWithDisplayFormat(inspectionTime.getPropertyInspectionTime().getStartTime()),
                                    LongExtKt.toStringWithDisplayFormat(inspectionTime.getPropertyInspectionTime().getEndTime())
                                    ));
        descriptionTextView.setText(LongExtKt.toStringWithDisplayFormat(inspectionTime.getPropertyInspectionTime().getStartTime()));
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
