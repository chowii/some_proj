package com.soho.sohoapp.home.editproperty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.soho.sohoapp.R;
import com.soho.sohoapp.abs.AbsActivity;
import com.soho.sohoapp.home.editproperty.data.PropertyImage;
import com.soho.sohoapp.home.editproperty.dialogs.AddPhotoDialog;
import com.soho.sohoapp.home.editproperty.photos.CameraPicker;
import com.soho.sohoapp.home.editproperty.photos.GalleryPicker;
import com.soho.sohoapp.home.portfolio.data.PortfolioProperty;
import com.soho.sohoapp.navigator.AndroidNavigator;
import com.soho.sohoapp.permission.AndroidPermissionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPropertyActivity extends AbsActivity implements EditPropertyContract.View {
    private static final String KEY_PORTFOLIO = "KEY_PORTFOLIO";

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.imageViewPager)
    ViewPager imageViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private EditPropertyContract.ViewActionsListener actionsListener;
    private EditPropertyPresenter presenter;
    private CameraPicker cameraPicker;
    private ImageHeaderViewPager pagerAdapter;
    private GalleryPicker galleryPicker;

    @NonNull
    public static Intent createIntent(@NonNull Context context, @NonNull PortfolioProperty property) {
        Intent intent = new Intent(context, EditPropertyActivity.class);
        intent.putExtra(KEY_PORTFOLIO, property);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);
        ButterKnife.bind(this);

        initView();

        presenter = new EditPropertyPresenter(this,
                AndroidNavigator.newInstance(this),
                AndroidPermissionManager.newInstance(this));
        presenter.startPresenting(savedInstanceState != null);
    }

    @Override
    protected void onDestroy() {
        presenter.stopPresenting();
        super.onDestroy();
    }

    @Override
    public void setActionsListener(EditPropertyContract.ViewActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    @Override
    public void showAddPhotoDialog() {
        AddPhotoDialog addPhotoDialog = new AddPhotoDialog(this);
        addPhotoDialog.show(new AddPhotoDialog.OnItemClickedListener() {
            @Override
            public void onTakeNewPhotoClicked() {
                actionsListener.onTakeNewPhotoClicked();
            }

            @Override
            public void onChooseFromGalleryClicked() {
                actionsListener.onChooseFromGalleryClicked();
            }
        });
    }

    @Override
    public void capturePhoto() {
        cameraPicker = new CameraPicker(this);
        cameraPicker.takePhoto(path -> actionsListener.onPhotoReady(path));
    }

    @Override
    public void setCurrentPropertyImage(int position) {
        imageViewPager.setCurrentItem(position);
    }

    @Override
    public void setPropertyImages(List<PropertyImage> propertyImages) {
        pagerAdapter.setData(propertyImages);
    }

    @Override
    public void pickImageFromGallery() {
        galleryPicker = new GalleryPicker(this);
        galleryPicker.choosePhoto(uri -> actionsListener.onPhotoPicked(uri));
    }

    @Override
    public PortfolioProperty getProperty() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        }
        return (PortfolioProperty) extras.getParcelable(KEY_PORTFOLIO);
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

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.edit_property_toolbar);
        toolbar.setNavigationOnClickListener(view -> actionsListener.onBackClicked());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_add_photo:
                    actionsListener.onAddPhotoClicked();
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    private void initTabs() {
        EditPropertyTabsAdapter adapter = new EditPropertyTabsAdapter(this, getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    private void initView() {
        initToolbar();
        initTabs();
        pagerAdapter = new ImageHeaderViewPager(imageViewPager.getContext());
        imageViewPager.setAdapter(pagerAdapter);
    }
}
