package com.soho.sohoapp.feature.marketplaceview.feature.detailview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.home.editproperty.ImageHeaderViewPager;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyHostTimeItem;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.contract.PropertyDetailContract;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder.presenter.PropertyDetailPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chowii on 31/8/17.
 */

public class PropertyDetailActivity extends AbsActivity
        implements PropertyDetailContract.ViewInteractable,
        PropertyDetailAuctionViewHolder.OnAddToCalenderClickListener
{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.image_view_pager)
    ViewPager imageViewPager;

    @BindView(R.id.address_line_1_textview)
    TextView addressLine1TextView;

    @BindView(R.id.address_line_2_textview)
    TextView addressLine2TextView;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    ImageHeaderViewPager pagerAdapter;
    public static final String EXTRA_PROPERTY_ID = "property_id";
    PropertyDetailPresenter presenter;
    int id;

    public static Intent createIntent(Context context, int propertyId) {
        Intent intent = new Intent(context, PropertyDetailActivity.class);
        intent.putExtra(EXTRA_PROPERTY_ID, propertyId);
        return intent;
    }

    public static Intent createIntent(Context context, int propertyId, int flags) {
        Intent intent = new Intent(context, PropertyDetailActivity.class);
        intent.putExtra(EXTRA_PROPERTY_ID, propertyId);
        intent.setFlags(flags);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        ButterKnife.bind(this);
        initToolbar();
        id = getIntent().getIntExtra(EXTRA_PROPERTY_ID, -1);
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
    public void populateView(Property property) {
        addressLine1TextView.setText(property.getLocation().getAddressLine1());
        addressLine2TextView.setText(property.getLocation().getAddressLine2());
        if (pagerAdapter == null)
            pagerAdapter = new ImageHeaderViewPager(imageViewPager.getContext());
        imageViewPager.setAdapter(pagerAdapter);
        pagerAdapter.setData(property.getPhotosAsImages());
        appBarLayout.setExpanded(true, true);
        if(property.getAgentMobileNumber() != null && !property.getAgentMobileNumber().isEmpty()) {
            toolbar.inflateMenu(R.menu.property_detail_toolbar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_call_agent) {
            callNumber(this, presenter.getProperty().getAgentMobileNumber());
        }
        return true;
    }

    public static void callNumber(Context context, String number) {
    Intent callIntent = new Intent(Intent.ACTION_DIAL);
    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
    callIntent.setData(Uri.parse("tel:" + number));
    context.startActivity(callIntent);
}

    //NOTE: It is important to enable BEFORE refreshing, and to disable after not refreshing to user can't pull to refresh
    @Override
    public void setRefreshing(boolean isRefreshing) {
        if(isRefreshing) { swipeRefreshLayout.setEnabled(true); }
        swipeRefreshLayout.setRefreshing(isRefreshing);
        if(!isRefreshing) { swipeRefreshLayout.setEnabled(false); }
    }

    @Override
    public void onAddToCalenderClicked(PropertyHostTimeItem timeItem, String state) {
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
        calendarIntent.setType("vnd.android.cursor.item/event");
        long startTime;
        if (state.equalsIgnoreCase("auction")) {
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, timeItem.retrievePropertyInspectionItem().getPropertyInspectionTime());
        } else {
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, timeItem.retrievePropertyInspectionItem().getPropertyInspectionTime().getEndTime());
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        }
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, timeItem.retrievePropertyInspectionItem().getPropertyInspectionTime().getStartTime());

        StringBuilder builder = new StringBuilder(state);
        builder.append(": ")
                .append(timeItem.retrieveLocation().getAddressLine1())
                .append(timeItem.retrieveLocation().getAddressLine2());

        calendarIntent.putExtra(Events.TITLE, builder.toString());
        startActivity(calendarIntent);
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
        appBarLayout.setExpanded(false, false);
        setTitle("");
    }

    @Override
    public void showError(@NotNull Throwable throwable) {
        handleError(throwable);
    }
}
