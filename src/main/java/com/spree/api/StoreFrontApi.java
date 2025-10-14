package com.spree.api;

import testframework.PropertiesManager;
import testframework.core.BaseApiService;
import testframework.enums.FrameworkSettings;

import static io.restassured.RestAssured.given;

public class StoreFrontApi extends BaseApiService {
    public StoreFrontApi() {
        super(PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.BASE_ESPREE_URL.getValue())
        , PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.STOREFRONT_PATH.getValue()));

        setRequestSpecification(
                given()
                        .baseUri(getServiceUrl())
                        .accept("application/vnd.api+json")
                        .contentType("application/vnd.api+json")
                        .log().all()
        );

    }
}
