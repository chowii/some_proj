package com.soho.sohoapp.feature.marketplaceview.filterview.savedfilters;

import com.soho.sohoapp.feature.marketplaceview.filterview.savedfilters.model.SavedFilterItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chowii on 30/8/17.
 */

class SavedFilterList {

    private List<SavedFilterItem> data;

    public List<SavedFilterItem> getData(){ return data == null ? new ArrayList<>() : data; }

}
