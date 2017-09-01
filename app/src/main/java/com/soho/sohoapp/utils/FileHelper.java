package com.soho.sohoapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;

public class FileHelper {
    private static final String FILE_DESCRIPTOR_READ_MODE = "r";
    private static final int IMAGE_QUALITY = 80;
    private static final int IMAGE_WIDTH = 1024;
    private static final int IMAGE_HEIGHT = 1024;
    private final Context context;

    private FileHelper(@NonNull Context context) {
        this.context = context;
    }

    public static FileHelper newInstance(@NonNull Context context) {
        return new FileHelper(context);
    }

    public byte[] compressPhoto(@NonNull Uri uri) {
        Bitmap bitmap = decodeSampledBitmapFromFile(uri);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, bos);
        return bos.toByteArray();
    }

    private Bitmap decodeSampledBitmapFromFile(Uri uri) {
        Bitmap image = null;
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, FILE_DESCRIPTOR_READ_MODE);

            if (parcelFileDescriptor != null) {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                // First decode with inJustDecodeBounds=true to check dimensions
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, IMAGE_WIDTH, IMAGE_HEIGHT);

                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false;
                image = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

                parcelFileDescriptor.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return image;

    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
