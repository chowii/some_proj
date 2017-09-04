package com.soho.sohoapp.network;

public interface Keys {
    interface Property {
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
}
