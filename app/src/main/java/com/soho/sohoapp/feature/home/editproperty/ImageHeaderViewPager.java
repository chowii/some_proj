package com.soho.sohoapp.feature.home.editproperty;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.soho.sohoapp.R;
import com.soho.sohoapp.data.models.Image;
import com.soho.sohoapp.imageloader.ImageLoader;
import com.soho.sohoapp.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

public class ImageHeaderViewPager extends PagerAdapter {

    private final LayoutInflater inflater;
    private final ImageLoader imageLoader;
    private final Logger logger;
    private List<Image> propertyImages;
    private ImageHeaderOnItemClickListener onItemClickListener;

    public ImageHeaderViewPager(@NonNull Context context) {
        propertyImages = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        imageLoader = DEPENDENCIES.getImageLoader();
        logger = DEPENDENCIES.getLogger();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        logger.d("Position: " + position);
        View itemView = inflater.inflate(R.layout.item_property_image, container, false);

        ImageView imageView = itemView.findViewById(R.id.image);
        Image image = propertyImages.get(position);

        ImageLoader.Params params = ImageLoader.Params.create()
                .view(imageView)
                .placeHolder(image.getHolder());

        if (image.getLargeImageUrl() != null) {
            imageLoader.load(params.url(image.getLargeImageUrl()));
        } else if (image.getFilePath() != null) {
            imageLoader.load(params.file(Uri.fromFile(new File(image.getFilePath()))));
        } else if (image.getUri() != null) {
            imageLoader.load(params.file(image.getUri()));
        } else {
            imageView.setImageResource(image.getDrawableId());
        }
        itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.imageOnClick(image);
            }
        });
        container.addView(itemView);
        return itemView;
    }

    public ImageHeaderOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(ImageHeaderOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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

    public void setData(@NonNull List<Image> newPropertyImageList) {
        if (propertyImages != null) {
            propertyImages = newPropertyImageList;
        }
        notifyDataSetChanged();
    }

    public List<Image> getDataSet() {
        return propertyImages;
    }
}
