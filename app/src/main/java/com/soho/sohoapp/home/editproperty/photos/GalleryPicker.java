package com.soho.sohoapp.home.editproperty.photos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

public class GalleryPicker {
    private static final int REQUEST_PICK_PHOTO = 1;
    private final Activity activity;
    private ImagePickedListener listener;

    public GalleryPicker(@NonNull Activity activity) {
        this.activity = activity;
    }

    public void choosePhoto(ImagePickedListener listener) {
        this.listener = listener;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_PHOTO);

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
