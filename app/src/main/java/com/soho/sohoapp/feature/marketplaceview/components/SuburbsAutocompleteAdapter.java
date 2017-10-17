package com.soho.sohoapp.feature.marketplaceview.components;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.soho.sohoapp.database.entities.Suburb;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jovan on 25/9/17.
 */

public class SuburbsAutocompleteAdapter extends ArrayAdapter<Suburb> implements Filterable {

    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    private GoogleApiClient googleApiClient;
    private ArrayList<Suburb> resultList;
    private AutocompleteFilter placeFilter;

    public SuburbsAutocompleteAdapter(Context context, GoogleApiClient googleApiClient, AutocompleteFilter filter) {
        super(context, android.R.layout.simple_expandable_list_item_2, android.R.id.text1);
        this.googleApiClient = googleApiClient;
        placeFilter = filter;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Suburb getItem(int position) {
        return resultList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = super.getView(position, convertView, parent);
        Suburb item = getItem(position);
        TextView textView1 = row.findViewById(android.R.id.text1);
        TextView textView2 = row.findViewById(android.R.id.text2);
        if (item != null) {
            textView1.setText(item.getName());
            String secondaryText = item.getSecondaryText();
            textView2.setText(secondaryText != null ? secondaryText : "");
        }
        return row;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new PlacesFilter(googleApiClient, placeFilter) {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                resultList = new ArrayList<>();
                if (results != null && results.count > 0) {
                    for(AutocompletePrediction prediction: ((List<AutocompletePrediction>) results.values)) {
                        Suburb suburb = new Suburb(prediction.getPlaceId(), prediction.getPrimaryText(STYLE_BOLD).toString());
                        suburb.setSecondaryText(prediction.getSecondaryText(STYLE_BOLD).toString());
                        resultList.add(suburb);
                    }
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}