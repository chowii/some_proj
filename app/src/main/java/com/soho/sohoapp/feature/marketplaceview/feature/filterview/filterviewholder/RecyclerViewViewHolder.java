package com.soho.sohoapp.feature.marketplaceview.feature.filterview.filterviewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soho.sohoapp.BaseFormViewHolder;
import com.soho.sohoapp.R;
import com.soho.sohoapp.feature.home.BaseModel;
import com.soho.sohoapp.feature.marketplaceview.components.MarketPlaceAdapter;
import com.soho.sohoapp.utils.Converter;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.soho.sohoapp.Dependencies.DEPENDENCIES;

/**
 * Created by chowii on 20/08/17.
 */

public class RecyclerViewViewHolder extends BaseFormViewHolder {

    @BindView(R.id.recyclerViewTest)
    RecyclerView recyclerView;

    public RecyclerViewViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindViewHolder(BaseModel model) {
        HashMap<String, Object> s = new HashMap<>();
        s.put("by_listing_type", "sale/auction");
        DEPENDENCIES.getSohoService().searchProperties(s)
                .map(Converter::toBasicProperties)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        properties -> recyclerView.setAdapter(new MarketPlaceAdapter(properties, null)),
                        throwable -> DEPENDENCIES.getLogger().d("throwable" + throwable.getMessage())
                );
    }

}
