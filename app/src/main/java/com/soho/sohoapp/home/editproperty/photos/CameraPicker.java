package com.soho.sohoapp.home.editproperty.photos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.soho.sohoapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraPicker {
    private static final int REQUEST_TAKE_PHOTO = 0;
    private final Activity activity;
    private String photoPath;
    private ImageCapturedListener listener;

    public CameraPicker(@NonNull Activity activity) {
        this.activity = activity;
    }

    public void takePhoto(ImageCapturedListener listener) {
        this.listener = listener;
        dispatchTakePictureIntent();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        photoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity, activity.getString(R.string.provider_authorities), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAKE_PHOTO) {
            listener.onImageCaptured(photoPath);
        }
    }

    public interface ImageCapturedListener {
        void onImageCaptured(String path);
    }
}
