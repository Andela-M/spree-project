package com.spree.api;

import io.restassured.response.Response;
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
    /** GET /products -> final URL: (host + /api/v2/storefront) + /products */
    public Response listProducts(String bearerToken) {
        return request()
                .auth().oauth2(bearerToken)
                .when()
                .get("/products");
    }

    // Add more storefront endpoints below following the same pattern:
    // - For POST/PATCH with a JSON body, set .contentType("application/json") and .body(...)


}
