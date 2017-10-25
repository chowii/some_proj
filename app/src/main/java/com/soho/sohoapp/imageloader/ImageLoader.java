package com.soho.sohoapp.imageloader;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.widget.ImageView;

import com.soho.sohoapp.utils.Preconditions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class ImageLoader {
    private final Context context;

    public ImageLoader(@NonNull Context context) {
        this.context = context;
    }

    public void load(@NonNull Params params) {
        Preconditions.checkIfNull(params.imageView, "Params.imageView must not be null.");
        RequestCreator creator;
        if (params.uri != null) {
            creator = Picasso.with(context)
                    .load(params.uri);
        } else {
            creator = Picasso.with(context)
                    .load(params.url);
        }
        if (params.width > 0 && params.height > 0) {
            creator.resize(params.width, params.height);
        }
        if (params.placeHolder != 0) {
            creator.placeholder(AppCompatResources.getDrawable(context, params.placeHolder))
                    .error(params.placeHolder);
        }
        if (params.centerCrop) {
            creator.centerCrop();
        }
        creator.into(params.imageView);
    }

    public static class Params {
        ImageView imageView;
        String url;
        Uri uri;
        int height;
        int width;
        boolean centerCrop;

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

        public Params height(int height) {
            this.height = height;
            return this;
        }

        public Params width(int width) {
            this.width = width;
            return this;
        }

        public Params centerCrop(boolean centerCrop) {
            this.centerCrop = centerCrop;
            return this;
        }
    }
}
