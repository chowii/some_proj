package com.soho.sohoapp.feature.marketplace;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soho.sohoapp.R;

/**
 * Created by chowii on 15/8/17.
 */

class PropertyPagerAdapter extends PagerAdapter {

    private final Context context;

    PropertyPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup layout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_image_view_pager, container, false);
        container.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
