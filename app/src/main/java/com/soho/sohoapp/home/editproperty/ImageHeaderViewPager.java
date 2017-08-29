package com.soho.sohoapp.home.editproperty;

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
import com.soho.sohoapp.home.editproperty.data.PropertyImage;
import com.soho.sohoapp.imageloader.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageHeaderViewPager extends PagerAdapter {

    private final LayoutInflater inflater;
    private final ImageLoader imageLoader;
    private List<PropertyImage> propertyImages;

    public ImageHeaderViewPager(@NonNull Context context) {
        propertyImages = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        imageLoader = Dependencies.INSTANCE.getImageLoader();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.item_property_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
        PropertyImage image = propertyImages.get(position);
        if (image.getFilePath() != null) {
            ImageLoader.Params params = ImageLoader.Params.create()
                    .file(Uri.fromFile(new File(image.getFilePath())))
                    .view(imageView)
                    .placeHolder(R.drawable.semiduplex);
            imageLoader.load(params);
        } else if (image.getUri() != null) {
            ImageLoader.Params params = ImageLoader.Params.create()
                    .file(image.getUri())
                    .view(imageView)
                    .placeHolder(R.drawable.semiduplex);
            imageLoader.load(params);
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
