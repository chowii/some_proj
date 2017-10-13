package com.soho.sohoapp.network;

public interface Keys {

    interface More {
        String TEXT = "text";
    }

    interface Database {
        String DB_NAME = "soho_db";
    }

    interface User {
        String EMAIL = "email";
        String PASSWORD = "password";
        String CURRENT_PASSWORD = "current_password";
        String PASSWORD_CONFIRMATION = "password_confirmation";
        String FIRST_NAME = "first_name";
        String LAST_NAME = "last_name";
        String COUNTRY = "country";
        String INTENDE_ROLE = "intended_role";
        String INTENDED_INTENTIONS = "intended_intentions";
        String INTENDED_INTENTIONS_ARRAY_MULTIPART = "intended_intentions[]";
        String AGENT_LICENCE_NUMBER = "agent_licence_number";
        String AVATAR = "avatar";
        String DOB = "dob";
        String AGENT_LOGO = "agent_logo";
    }

    interface Intention {
        String BUYING = "buying";
        String SELLING = "selling";
        String RENTING = "renting";
    }

    interface Role {
        String USER = "user";
        String AGENT = "agent";
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
        String PROPERTY_FINANCE_ATTRIBUTES = "property_finance_attributes";
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

    interface InspectionTime {
        String START_TIME = "start_time";
        String END_TIME = "end_time";
    }

    interface PropertyFinance {
        String PURCHASE_PRICE = "purchase_price";
        String LOAN_AMOUNT = "loan_amount";
        String ESTIMATED_VALUE = "estimated_value";
        String IS_RANTED = "is_rented";
        String ESTIMATED_RENT = "estimated_rent";
        String ACTUAL_RENT = "actual_rent";
        String LEASED_TO = "leased_to";
    }

    interface Filter {
        String FILTER_BY_GOOGLE_PLACES = "by_google_places";
        String FILTER_PLACE_IDS = "place_ids";
        String FILTER_DISTANCE = "distance";
        String FILTER_MIN_SALE_PRICE = "by_min_sell_price";
        String FILTER_MAX_SALE_PRICE = "by_max_sell_price";
        String FILTER_MIN_RENT_PRICE = "by_min_rent_price";
        String FILTER_MAX_RENT_PRICE = "by_max_rent_price";
        String FILTER_BY_BEDROOM_COUNT = "by_bedroom_count";
        String FILTER_BY_BATHROOM_COUNT = "by_bathroom_count";
        String FILTER_BY_CARSPOT_COUNT = "by_carspot_count";
        String FILTER_BY_PROPERTY_TYPE = "by_property_types";
        String FILTER_BY_LISTING_TYPE = "by_listing_type";
        String FILTER_ALL_PROPERTIES = "all_properties";
        String FILTER_PER_PAGE = "per_page";
        String FILTER_PAGE = "page";
    }

    interface Pagination {
        String PAGINATION_PAGE = "x-page";
        String PAGINATION_NEXT_PAGE = "x-next-page";
        String PAGINATION_PER_PAGE = "x-per-page";
        String PAGINATION_TOTAL_PAGES = "x-total-pages";
        String PAGINATION_TOTAL_ITEMS = "x-total";
        String PAGINATION_OFFSET = "x-offset";
    }

    interface Verification {
        String ATTACHMENT = "attachments_attributes[%d][file]";
        String PIN_CODE = "code";
    }
}
