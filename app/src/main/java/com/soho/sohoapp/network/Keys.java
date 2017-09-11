package com.soho.sohoapp.network;

public interface Keys {

    interface Property {

        String PROPRETY_ID = "property_id";
        String RELATION = "relation";
        String BEDROOMS = "property[bedrooms]";
        String BATHROOMS = "property[bathrooms]";
        String CARSPOTS = "property[carspots]";
        String IS_INVESTMENT = "property[is_investment]";
        String TYPE_OF_PROPERTY = "property[type_of_property]";

        String ADDRESS1 = "property[location_attributes][address_1]";
        String ADDRESS2 = "property[location_attributes][address_2]";
        String SUBURB = "property[location_attributes][suburb]";
        String STATE = "property[location_attributes][state]";
        String POSTCODE = "property[location_attributes][postcode]";
        String COUNTRY = "property[location_attributes][country]";
        String LATITUDE = "property[location_attributes][latitude]";
        String LONGITUDE = "property[location_attributes][longitude]";
        String IMAGE = "property_photos_attributes[0][image]";
    }

    interface Filter {
        String FILTER_BY_GOOGLE_PLACES = "by_google_places[distance]";
        String FILTER_MIN_SALE_PRICE = "by_min_sell_price";
        String FILTER_MAX_SALE_PRICE = "by_max_sell_price";
        String FILTER_MIN_RENT_PRICE = "by_min_rent_price";
        String FILTER_MAX_RENT_PRICE = "by_max_rent_price";
        String FILTER_BY_BEDROOM_COUNT = "by_bedroom_count";
        String FILTER_BY_BATHROOM_COUNT = "by_bathroom_count";
        String FILTER_BY_CARSPOT_COUNT = "by_carspot_count";
    }
}
