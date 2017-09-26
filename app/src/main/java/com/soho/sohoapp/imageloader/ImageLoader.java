package com.soho.sohoapp.imageloader;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.widget.ImageView;

import com.soho.sohoapp.utils.Preconditions;
import com.squareup.picasso.Picasso;

public class ImageLoader {
    private final Context context;

    public ImageLoader(@NonNull Context context) {
        this.context = context;
    }

    public void load(@NonNull Params params) {
        Preconditions.checkIfNull(params.imageView, "Params.imageView must not be null.");
        if (params.uri != null) {
            Picasso.with(context)
                    .load(params.uri)
                    .placeholder(AppCompatResources.getDrawable(context, params.placeHolder))
                    .error(params.placeHolder)
                    .into(params.imageView);
        } else {
            Picasso.with(context)
                    .load(params.url)
                    .placeholder(AppCompatResources.getDrawable(context, params.placeHolder))
                    .error(params.placeHolder)
                    .into(params.imageView);
        }
    }

    public static class Params {
        ImageView imageView;
        String url;
        Uri uri;

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

        public Params file(@NonNull Uri uri) {
            this.uri = uri;
            return this;
        }

        public Params placeHolder(@DrawableRes int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }
    }
}
