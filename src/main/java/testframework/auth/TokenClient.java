package testframework.auth;

import io.restassured.RestAssured;
import testframework.PropertiesManager;
import testframework.enums.FrameworkSettings;

import java.util.HashMap;
import java.util.Map;

public final class TokenClient {
    private TokenClient() {}

    public static String getStoreFrontToken(String username, String password) {

        String tokenUrl = PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.BASE_ESPREE_URL.getValue())
                + PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.TOKEN_PATH.getValue());

        return RestAssured.given()
                .baseUri(tokenUrl)
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("username", username)
                .formParam("password", password)
                .post("")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("access_token");

    }

    public static String getPlatformToken(String clientId, String clientSecret) {
        String tokenUrl = PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.BASE_ESPREE_URL.getValue())
                + PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.TOKEN_PATH.getValue());



        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "client_credentials");
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("scope", "admin");

        return RestAssured.given()
                .baseUri(tokenUrl)
                .contentType("application/json")
                .body(body)
                .when()
                .post("")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("access_token");
    }

    }
