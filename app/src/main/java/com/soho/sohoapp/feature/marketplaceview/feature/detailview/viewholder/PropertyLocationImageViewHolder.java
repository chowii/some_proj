package com.soho.sohoapp.feature.marketplaceview.feature.detailview.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.soho.sohoapp.BaseViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.marketplaceview.feature.detailview.model.PropertyLocationImageItem;
import com.soho.sohoapp.imageloader.ImageLoader;

import butterknife.BindView;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

/**
 * Created by chowii on 6/9/17.
 */

public class PropertyLocationImageViewHolder extends BaseViewHolder<PropertyLocationImageItem> {

    @BindView(R.id.image)
    ImageView imageView;

    private final String mApiKey;

    public PropertyLocationImageViewHolder(View itemView, String apiKey) {
        super(itemView);
        mApiKey = apiKey;
    }

    @Override
    public void onBindViewHolder(PropertyLocationImageItem model) {

        StringBuilder imageUrlBuilder = new StringBuilder(model.retrieveImageUrl());
        imageUrlBuilder.append("&key=").append(mApiKey);
        ImageLoader imageLoader = DEPENDENCIES.getImageLoader();
        ImageLoader.Params params = ImageLoader.Params.create()
                                        .url(imageUrlBuilder.toString())
                                        .view(imageView)
                                        .placeHolder(R.drawable.apartment);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageLoader.load(params);
        System.gc();
    }
}
