package com.soho.sohoapp.imageloader;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.soho.sohoapp.utils.Preconditions;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageLoader {
    private final Context context;

    public ImageLoader(@NonNull Context context) {
        this.context = context;
    }

    public void load(@NonNull Params params) {
        Preconditions.checkIfNull(params.imageView, "Params.imageView must not be null.");
        if (params.file != null) {
            Picasso.with(context)
                    .load(params.file)
                    .placeholder(params.placeHolder)
                    .into(params.imageView);
        } else {
            Picasso.with(context)
                    .load(params.url)
                    .placeholder(params.placeHolder)
                    .into(params.imageView);
        }
    }

    public static class Params {
        ImageView imageView;
        String url;
        File file;

        @DrawableRes
        int placeHolder;

        private Params() {
            //empty
        }

        public static Params create() {
            return new Params();
        }

        public Params url(@NonNull String url) {
            this.url = url;
            return this;
        }

        public Params view(@NonNull ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Params file(@NonNull File file) {
            this.file = file;
            return this;
        }

        public Params placeHolder(@DrawableRes int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }
    }
}
