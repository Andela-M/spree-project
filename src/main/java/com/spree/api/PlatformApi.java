package com.spree.api;

import com.google.gson.annotations.SerializedName;
import io.restassured.response.Response;
import testframework.PropertiesManager;
import testframework.auth.TokenClient;
import testframework.core.BaseApiService;
import testframework.enums.FrameworkSettings;

import static io.restassured.RestAssured.given;

public class PlatformApi extends BaseApiService {
    public PlatformApi() {
        super(PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.BASE_ESPREE_URL.getValue())
                , PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.PLATFORM_PATH.getValue()));
        setRequestSpecification(
                given()
                        .baseUri(PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.BASE_ESPREE_URL.getValue()))
                        .basePath(PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.PLATFORM_PATH.getValue()))
                        .accept("application/vnd.api+json")
                        .contentType("application/json")
        );
    }

    private static final class UserEnvelope {
        @SerializedName("user") final User user;
        UserEnvelope(User user) { this.user = user; }
    }

    public String createUserAndExtractId(User userBody) {
        String adminToken = TokenClient.getPlatformToken();

        return request()
                .auth().oauth2(adminToken)
                .body(new UserEnvelope(userBody))
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .path("data.id");
    }

    public Response listUsers(String bearerToken) {
        return request()
                .auth().oauth2(bearerToken)
                .when()
                .get("/users");
    }
}
