package com.mikeoshadami.marketplace.extended.config;

public interface ApplicationUrl {

    String BASE_CONTEXT_URL = "api/v1/store";

    String OPEN_STORE = "/open";

    String LOOKUP_STORE = "/lookup/{alias}";

    String CREATE_PRODUCT_CATEGORY = "/product/category/create";

    String STORE_PRODUCT_CATEGORIES = "/product/category/{alias}";


}
