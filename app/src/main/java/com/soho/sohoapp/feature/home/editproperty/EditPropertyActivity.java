package com.soho.sohoapp.feature.home.editproperty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.models.Image;
import com.soho.sohoapp.data.models.Location;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.data.models.PropertyFinance;
import com.soho.sohoapp.dialogs.LoadingDialog;
import com.soho.sohoapp.feature.home.addproperty.data.PropertyType;
import com.soho.sohoapp.feature.home.editproperty.dialogs.AddPhotoDialog;
import com.soho.sohoapp.feature.home.editproperty.overview.EditOverviewFragment;
import com.soho.sohoapp.feature.home.editproperty.photos.CameraPicker;
import com.soho.sohoapp.feature.home.editproperty.photos.GalleryPicker;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.permission.PermissionManagerImpl;
import com.soho.sohoapp.utils.FileHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPropertyActivity extends AbsActivity implements
        EditPropertyContract.ViewInteractable,
        EditOverviewFragment.EditPropertyListener {
    private static final String KEY_PROPERTY_ID = "KEY_PROPERTY_ID";

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.imageViewPager)
    ViewPager imageViewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.addressLine1)
    TextView addressLine1;

    @BindView(R.id.addressLine2)
    TextView addressLine2;

    private EditPropertyContract.ViewPresentable presentable;
    private EditPropertyPresenter presenter;
    private CameraPicker cameraPicker;
    private ImageHeaderViewPager pagerAdapter;
    private GalleryPicker galleryPicker;
    private View loadingView;
    private LoadingDialog loadingDialog;
    private boolean isEdited;
    private boolean editedEnabled;

    @NonNull
    public static Intent createIntent(@NonNull Context context, int propertyId) {
        Intent intent = new Intent(context, EditPropertyActivity.class);
        intent.putExtra(KEY_PROPERTY_ID, propertyId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);
        ButterKnife.bind(this);

        initView();
        appBarLayout.setExpanded(false, false);

        presenter = new EditPropertyPresenter(this,
                NavigatorImpl.newInstance(this),
                PermissionManagerImpl.newInstance(this),
                FileHelper.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @Override
    public void setPresentable(EditPropertyContract.ViewPresentable presentable) {
        this.presentable = presentable;
    }

    @Override
    public void showAddPhotoDialog() {
        AddPhotoDialog addPhotoDialog = new AddPhotoDialog(this);
        addPhotoDialog.show(new AddPhotoDialog.OnItemClickedListener() {
            @Override
            public void onTakeNewPhotoClicked() {
                presentable.onTakeNewPhotoClicked();
            }

            @Override
            public void onChooseFromGalleryClicked() {
                presentable.onChooseFromGalleryClicked();
            }
        });
    }

    @Override
    public void capturePhoto() {
        cameraPicker = new CameraPicker(this);
        cameraPicker.takePhoto(path -> presentable.onPhotoReady(path));
    }

    @Override
    public void setCurrentPropertyImage(int position) {
        imageViewPager.setCurrentItem(position);
    }

    @Override
    public void setPropertyImages(List<Image> images) {
        pagerAdapter.setData(images);
    }

    @Override
    public void pickImageFromGallery() {
        galleryPicker = new GalleryPicker(this);
        galleryPicker.choosePhoto(uri -> presentable.onPhotoPicked(uri));
    }

    @Override
    public int getPropertyId() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return 0;
        }
        return extras.getInt(KEY_PROPERTY_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (cameraPicker != null) {
            cameraPicker.onActivityResult(requestCode, resultCode);
        }
        if (galleryPicker != null) {
            galleryPicker.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showLoadingView() {
        ViewGroup content = findViewById(android.R.id.content);
        loadingView = getLayoutInflater().inflate(R.layout.view_loading, content, false);
        content.addView(loadingView);
    }

    @Override
    public void hideLoadingView() {
        if (loadingView != null) {
            ViewGroup content = findViewById(android.R.id.content);
            content.removeView(loadingView);
            loadingView = null;
        }
    }

    @Override
    public void showError(@NonNull Throwable throwable) {
        handleError(throwable);
    }

    @Override
    public void showAddress1(String address) {
        addressLine1.setText(address);
    }

    @Override
    public void showAddress2(String address) {
        addressLine2.setText(address);
    }

    @Override
    public void initTabs(Property property, ArrayList<PropertyType> propertyTypes) {
        EditPropertyTabsAdapter adapter = new EditPropertyTabsAdapter(this,
                getSupportFragmentManager(),
                property,
                propertyTypes);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        appBarLayout.setExpanded(true, true);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show(getString(R.string.common_loading));
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public void showToolbarActions() {
        toolbar.getMenu().findItem(R.id.action_save).setVisible(true);
        toolbar.getMenu().findItem(R.id.action_add_photo).setVisible(true);
    }

    @Override
    public void onPropertyAddressChanged(Location location) {
        presentable.onPropertyAddressChanged(location);
        setIsEdited();
    }

    @Override
    public void onRoomsNumberChanged(double bedrooms, double bathrooms, double carspots) {
        presentable.onRoomsNumberChanged(bedrooms, bathrooms, carspots);
        setIsEdited();
    }

    @Override
    public void onPropertyTypeChanged(String type) {
        presentable.onPropertyTypeChanged(type);
        setIsEdited();
    }

    @Override
    public void onRenovationChanged(String renovation) {
        presentable.onRenovationChanged(renovation);
        setIsEdited();
    }

    @Override
    public void onInvestmentStatusChanged(boolean isInvestment) {
        presentable.onInvestmentStatusChanged(isInvestment);
        setIsEdited();
    }

    @Override
    public void onPropertyStatusChanged(String propertyStatus) {
        presentable.onPropertyStatusChanged(propertyStatus);
        setIsEdited();
    }

    @Override
    public void onPropertyFinanceChanged(PropertyFinance finance) {
        presentable.onPropertyFinanceChanged(finance);
        setIsEdited();
    }

    @Override
    public void onPropertyChangedEnabled() {
        editedEnabled = true;
    }

    public void setIsEdited() {
        if (editedEnabled)
            this.isEdited = true;
    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.edit_property_toolbar);
        toolbar.setNavigationOnClickListener(view -> showDiscardConfirmationDialog());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_add_photo:
                    presentable.onAddPhotoClicked();
                    break;
                case R.id.action_save:
                    presentable.onSaveClicked();
                    break;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        showDiscardConfirmationDialog();
    }

    private void showDiscardConfirmationDialog() {
        if (isEdited) {
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage(R.string.dialog_want_save)
                    .setPositiveButton(R.string.edit_property_save, (dialogInterface, i) -> {
                        presentable.onSaveClicked();
                    })
                    .setNegativeButton(R.string.discard, (dialogInterface, i) -> {
                        presentable.onBackClicked();
                    })
                    .show();
        } else {
            presentable.onBackClicked();
        }
    }

    private void initView() {
        initToolbar();
        pagerAdapter = new ImageHeaderViewPager(imageViewPager.getContext());
        imageViewPager.setAdapter(pagerAdapter);
        pagerAdapter.setOnItemClickListener(image ->
                presenter.onHeaderPhotoClicked(pagerAdapter.getDataSet(), imageViewPager.getCurrentItem()));
    }
}
