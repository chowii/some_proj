package com.soho.sohoapp.feature.home.editproperty;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.soho.sohoapp.Dependencies;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.editproperty.data.PropertyImage;
import com.soho.sohoapp.imageloader.ImageLoader;
import com.soho.sohoapp.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageHeaderViewPager extends PagerAdapter {

    private final LayoutInflater inflater;
    private final ImageLoader imageLoader;
    private final Logger logger;
    private List<PropertyImage> propertyImages;

    public ImageHeaderViewPager(@NonNull Context context) {
        propertyImages = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        imageLoader = Dependencies.INSTANCE.getImageLoader();
        logger = Dependencies.INSTANCE.getLogger();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        logger.d("Position: " + position);
        View itemView = inflater.inflate(R.layout.item_property_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
        PropertyImage image = propertyImages.get(position);

        ImageLoader.Params params = ImageLoader.Params.create()
                .view(imageView)
                .placeHolder(image.getHolder());

        if (image.getImageUrl() != null) {
            imageLoader.load(params.url(image.getImageUrl()));
        } else if (image.getFilePath() != null) {
            imageLoader.load(params.file(Uri.fromFile(new File(image.getFilePath()))));
        } else if (image.getUri() != null) {
            imageLoader.load(params.file(image.getUri()));
        } else {
            imageView.setImageResource(image.getDrawableId());
        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public int getCount() {
        return propertyImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setData(@NonNull List<PropertyImage> newPropertyImageList) {
        if (propertyImages != null) {
            propertyImages = newPropertyImageList;
        }
        notifyDataSetChanged();
    }
}
