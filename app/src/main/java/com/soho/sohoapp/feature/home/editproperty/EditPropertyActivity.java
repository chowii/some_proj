package com.soho.sohoapp.feature.home.editproperty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.data.models.Image;
import com.soho.sohoapp.data.models.Property;
import com.soho.sohoapp.feature.home.editproperty.dialogs.AddPhotoDialog;
import com.soho.sohoapp.feature.home.editproperty.photos.CameraPicker;
import com.soho.sohoapp.feature.home.editproperty.photos.GalleryPicker;
import com.soho.sohoapp.navigator.NavigatorImpl;
import com.soho.sohoapp.permission.PermissionManagerImpl;
import com.soho.sohoapp.utils.FileHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPropertyActivity extends AbsActivity implements EditPropertyContract.ViewInteractable {
    private static final String KEY_PROPERTY_ID = "KEY_PROPERTY_ID";

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.imageViewPager)
    ViewPager imageViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

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

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull int propertyId) {
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
    public void showLoadingDialog() {
        ViewGroup content = findViewById(android.R.id.content);
        loadingView = getLayoutInflater().inflate(R.layout.view_loading, content, false);
        content.addView(loadingView);
    }

    @Override
    public void hideLoadingDialog() {
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
    public void initTabs(Property property) {
        EditPropertyTabsAdapter adapter = new EditPropertyTabsAdapter(this, getSupportFragmentManager(), property);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.edit_property_toolbar);
        toolbar.setNavigationOnClickListener(view -> presentable.onBackClicked());
        toolbar.setOnMenuItemClickListener(item -> {
            if (R.id.action_add_photo == item.getItemId()) {
                presentable.onAddPhotoClicked();
            }
            return false;
        });
    }

    private void initView() {
        initToolbar();
        pagerAdapter = new ImageHeaderViewPager(imageViewPager.getContext());
        imageViewPager.setAdapter(pagerAdapter);
    }
}
