package com.soho.sohoapp.network;

public interface Keys {

    interface More {
        String TEXT = "text";
    }

    interface User {
        String EMAIL = "email";
        String PASSWORD = "password";
        String FIRST_NAME = "first_name";
        String LAST_NAME = "last_name";
        String COUNTRY = "country";
    }

    interface Property {
        String PROPRETY_ID = "property_id";
        String RELATION = "relation";
        String DESCRIPTION = "description";
        String BEDROOMS = "property[bedrooms]";
        String BATHROOMS = "property[bathrooms]";
        String CARSPOTS = "property[carspots]";
        String IS_INVESTMENT = "property[is_investment]";
        String TYPE_OF_PROPERTY = "property[type_of_property]";
        String IMAGE = "property_photos_attributes[0][image]";
    }

    interface Location {
        String FULL_ADDRESS = "property[location_attributes][full_address]";
        String ADDRESS1 = "property[location_attributes][address_1]";
        String ADDRESS2 = "property[location_attributes][address_2]";
        String SUBURB = "property[location_attributes][suburb]";
        String STATE = "property[location_attributes][state]";
        String POSTCODE = "property[location_attributes][postcode]";
        String COUNTRY = "property[location_attributes][country]";
        String LATITUDE = "property[location_attributes][latitude]";
        String LONGITUDE = "property[location_attributes][longitude]";
    }

    interface PropertyListing {
        String ID = "id";
        String STATE = "state";
        String SALE_OFFERS = "receive_sales_offers";
        String RENT_OFFERS = "receive_rent_offers";
        String SALE_TITLE = "sale_title";
        String RENT_TITLE = "rent_title";
        String AUCTION_TITLE = "auction_title";
        String DISCOVERABLE_TITLE = "discoverable_title";
        String ON_SITE_AUCTION = "on_site_auction";
        String AUCTION_DATE = "auction_date";
        String AUCTION_TIME = "auction_time";
        String RENT_PAYMENT_FREQUENCY = "rent_payment_frequency";
        String AVAILABLE_FROM = "available_from";
        String APPOINTMENT_ONLY = "appointment_only";
    }

    interface PropertyFinance {
        String PURCHASE_PRICE = "property_finance_attributes[purchase_price]";
        String LOAN_AMOUNT = "property_finance_attributes[loan_amount]";
        String ESTIMATED_VALUE = "property_finance_attributes[estimated_value]";
        String IS_RANTED = "property_finance_attributes[is_rented]";
        String ESTIMATED_RENT = "property_finance_attributes[estimated_rent]";
        String ACTUAL_RENT = "property_finance_attributes[actual_rent]";
        String LEASED_TO = "property_finance_attributes[leased_to]";
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
        String FILTER_BY_PROPERTY_TYPE = "by_property_types";
    }
}
