package com.soho.sohoapp.feature.home.editproperty.photos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.soho.sohoapp.R;

public class GalleryPicker {
    private static final String IMAGE_TYPE_ALL = "image/*";
    private static final int REQUEST_PICK_PHOTO = 1;
    private Activity activity;
    private Fragment fragment;
    private ImagePickedListener listener;

    public GalleryPicker(@NonNull Activity activity) {
        this.activity = activity;
    }

    public GalleryPicker(@NonNull Fragment fragment) {
        this.fragment = fragment;
    }

    public void choosePhoto(ImagePickedListener listener) {
        this.listener = listener;
        Intent intent = new Intent();
        intent.setType(IMAGE_TYPE_ALL);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (fragment != null) {
            Intent chooser = Intent.createChooser(intent, fragment.getString(R.string.edit_property_image_chooser_title));
            fragment.startActivityForResult(chooser, REQUEST_PICK_PHOTO);
        } else {
            Intent chooser = Intent.createChooser(intent, activity.getString(R.string.edit_property_image_chooser_title));
            activity.startActivityForResult(chooser, REQUEST_PICK_PHOTO);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PICK_PHOTO) {
            if (data != null && data.getData() != null) {
                listener.onImagePicked(data.getData());
            }
        }
    }

    public interface ImagePickedListener {
        void onImagePicked(Uri path);
    }
}
