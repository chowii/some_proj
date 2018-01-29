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
        String ROLE = "role";
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
        String GUEST = "guest";
    }

    interface Property {
        String PROPRETY_ID = "property_id";
        String RELATION = "relation";
        String DESCRIPTION = "description";
        String RENOVATION_DETAILS = "renovation_details";
        String BEDROOMS = "bedrooms";
        String BATHROOMS = "bathrooms";
        String CARSPOTS = "carspots";
        String TYPE_OF_PROPERTY = "type_of_property";
        String IS_INVESTMENT = "is_investment";
        String LAND_SIZE_MEASUREMENT = "land_size_measurement";
        String LAND_SIZE = "land_size";
        String BEDROOMS_ATTRIBUTE = "property[bedrooms]";
        String BATHROOMS_ATTRIBUTE = "property[bathrooms]";
        String CARSPOTS_ATTRIBUTE = "property[carspots]";
        String IS_INVESTMENT_ATTRIBUTE = "property[is_investment]";
        String TYPE_OF_PROPERTY_ATTRIBUTE = "property[type_of_property]";
        String IMAGE = "property_photos_attributes[0][image]";
        String PROPERTY_FINANCE_ATTRIBUTES = "property_finance_attributes";
        String PROPERTY_LOCATION_ATTRIBUTES = "location_attributes";
    }

    interface LocationAttributes {
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

    interface Location {
        String FULL_ADDRESS = "full_address";
        String ADDRESS1 = "address_1";
        String ADDRESS2 = "address_2";
        String SUBURB = "suburb";
        String STATE = "state";
        String POSTCODE = "postcode";
        String COUNTRY = "country";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
        String MASK_ADDRESS = "mask_address";
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
        String FILTER_RENT_FREQUENCY = "by_rent_frequency";
        String FILTER_BY_BEDROOM_COUNT = "by_bedroom_count";
        String FILTER_BY_BATHROOM_COUNT = "by_bathroom_count";
        String FILTER_BY_CARSPOT_COUNT = "by_carspot_count";
        String FILTER_BY_PROPERTY_TYPE = "by_property_types";
        String FILTER_BY_ORDER = "by_order";
        String FILTER_DIRECTION = "direction";
        String FILTER_COLUMN = "column";
        String FILTER_BY_LISTING_TYPE = "by_listing_type";
        String FILTER_ALL_PROPERTIES = "all_properties";
        String FILTER_PER_PAGE = "per_page";
        String FILTER_PAGE = "page";
        double FILTER_STUDIO_SERVER_VALUE = 0.5;
        double FILTER_BEDROOM_SERVER_VALUE = 0.0;

    }

    interface OrderBy {
        String SALE_VALUE = "property_finances.estimated_value";
        String RENT = "property_finances.estimated_rent";
        String CREATED_AT = "properties.created_at";
        String UPDATED_AT = "properties.updated_at";
        String LISTED_AT = "properties.listed_at";
    }

    interface OrderDirection {
        String ASCENDING = "asc";
        String DESCENDING = "desc";
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
        String ATTACHMENT_ID = "id";
        String ATTACHMENT_DESTROY = "_destroy";
        String ATTACHMENT_ATTRIBUTES = "attachments_attributes";
        String PIN_CODE = "code";
    }

    interface DeeplinkingNotifications {
        String SCHEME = "sohoapp";
        String AUTH_PROPERTIES = "properties";
        String ACTION_EDIT = "edit";
        String ACTION_VIEW = "view";
    }

    interface PropertyFile {
        String FILE_DOCUMENT_TYPE = "document_type";
        String FILE_IS_COST = "is_cost";
        String FILE_AMOUNT = "amount";
        String FILE_ATTACHMENT = "attachment";
    }

    interface PropertyEnquire {
        String RESOURCE_ID = "resource_id";
        String PROPERTY_ID = "property_id";
        String CHAT_TYPE = "chat_type";
        String CHAT_PROPERTY = "property";
        String CHAT_USER = "USER";
    }

    interface DeviceInfo {
        String DEVICE_TYPE_KEY = "device_type";
        String DEVICE_TYPE = "DeviceGcm";
        String DEVICE_TOKEN_KEY = "device_token";
    }

    abstract class ChatImage {
        public static String CHAT_ATTACHMENT_ENDPOINT_FORMAT = "http://staging.sohoapp.com/api/twilio/conversations/%d/attachments/%d?authorization=%s";
        public static String CHAT_ATTACH_IMAGE = "file";
        public static String CHAT_ATTACH_IMAGE_FILE_NAME = "original_filename";
    }
}
