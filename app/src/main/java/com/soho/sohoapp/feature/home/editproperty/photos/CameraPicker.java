package com.soho.sohoapp.feature.home.editproperty.photos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import com.soho.sohoapp.R;
import com.soho.sohoapp.utils.DateUtils;

import java.io.File;
import java.io.IOException;

public class CameraPicker {
    private static final int REQUEST_TAKE_PHOTO = 0;
    private Activity activity;
    private Fragment fragment;
    private String photoPath;
    private ImageCapturedListener listener;
    private Context context;

    public CameraPicker(@NonNull Activity activity) {
        this.activity = activity;
        context = activity;
    }

    public CameraPicker(@NonNull Fragment fragment) {
        this.fragment = fragment;
        context = fragment.getActivity();
    }

    public void takePhoto(ImageCapturedListener listener) {
        this.listener = listener;
        dispatchTakePictureIntent();
    }

    private File createImageFile() throws IOException {
        String timeStamp = DateUtils.getDateFormatForFileName();
        String imageFileName = String.format("JPEG_%s_", timeStamp);
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        photoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context, context.getString(R.string.provider_authorities), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (fragment != null) {
                    fragment.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                } else {
                    activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
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
