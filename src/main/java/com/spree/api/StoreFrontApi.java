package com.spree.api;

import com.google.gson.annotations.SerializedName;
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

    private static final class UserEnvelope {
        @SerializedName("user") final User user;
        UserEnvelope(User user) { this.user = user; }
    }

    public Response createAccount(User userBody) {
        Response response = request()
                .body(new UserEnvelope(userBody))
                .when()
                .post("/account");

        System.out.println("CREATE user response status: " + response.getStatusCode());
        System.out.println("CREATE user response body: " + response.asString());

        return response;

    }
}
