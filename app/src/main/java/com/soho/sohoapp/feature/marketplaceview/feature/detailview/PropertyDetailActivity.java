package com.soho.sohoapp.feature.marketplaceview.feature.detailview;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyHostTimeItem;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.contract.PropertyDetailContract;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.presenter.PropertyDetailPresenter;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chowii on 31/8/17.
 */

public class PropertyDetailActivity extends AppCompatActivity
    implements PropertyDetailContract.ViewInteractable,
        PropertyDetailAuctionViewHolder.OnAddToCalenderClickListener
{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.property_detail_camera_button)
    void onPropertyDetailCameraOnClick(){
        Log.d("LOG_TAG---", "onPropertyDetailCameraOnClick: clicked");
    }

    PropertyDetailPresenter presenter;
    int id;

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra("property_id", -1);

        presenter = new PropertyDetailPresenter(this);

        presenter.startPresenting();
        presenter.retrieveProperty(id);
    }

    @Override
    public void configureAdapter(List<BaseModel> model) {
        recyclerView.setAdapter(new MarketPlaceDetailAdapter(model, this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onAddToCalenderClicked(PropertyHostTimeItem timeItem, String state) {
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
        calendarIntent.setType("vnd.android.cursor.item/event");
        long startTime;
            Calendar c = timeItem.propertyAuctionItem.retrieveAuctionDate();
        if(state.equalsIgnoreCase("auction")) {
            startTime = c.getTimeInMillis();
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        } else {
            startTime = c.getTimeInMillis();
            long endTime = c.getTimeInMillis();
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        }
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);

        StringBuilder builder = new StringBuilder(state);
        builder.append(": ")
                .append(timeItem.propertyAuctionItem.getLocation().retrieveAddress1())
                .append(timeItem.propertyAuctionItem.getLocation().retrieveAddress2());

        calendarIntent.putExtra(Events.TITLE, builder.toString());
        startActivity(calendarIntent);
    }



}
