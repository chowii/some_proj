package com.soho.sohoapp.home.editproperty.photos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.soho.sohoapp.R;

public class GalleryPicker {
    private static final String IMAGE_TYPE_ALL = "image/*";
    private static final int REQUEST_PICK_PHOTO = 1;
    private final Activity activity;
    private ImagePickedListener listener;

    public GalleryPicker(@NonNull Activity activity) {
        this.activity = activity;
    }

    public void choosePhoto(ImagePickedListener listener) {
        this.listener = listener;
        Intent intent = new Intent();
        intent.setType(IMAGE_TYPE_ALL);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent chooser = Intent.createChooser(intent, activity.getString(R.string.edit_property_image_chooser_title));
        activity.startActivityForResult(chooser, REQUEST_PICK_PHOTO);
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
