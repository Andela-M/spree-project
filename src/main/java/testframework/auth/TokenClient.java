package testframework.auth;

import io.restassured.RestAssured;
import testframework.PropertiesManager;
import testframework.enums.FrameworkSettings;
import testframework.enums.PlatformAdminToken;

public final class TokenClient {
    private TokenClient() {
    }

    public static String getStoreFrontToken(String username, String password) {
        String host = PropertiesManager.getConfigProperties()
                .getProperty(FrameworkSettings.BASE_ESPREE_URL.getValue());
        String tokenPath = PropertiesManager.getConfigProperties()
                .getProperty(FrameworkSettings.TOKEN_PATH.getValue());

        return RestAssured.given()
                .baseUri(host)            // host only
                .basePath(tokenPath)      // path only
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("access_token");
    }

    public static String getPlatformToken() {
        String host = PropertiesManager.getConfigProperties()
                .getProperty(FrameworkSettings.BASE_ESPREE_URL.getValue());
        String tokenPath = PropertiesManager.getConfigProperties()
                .getProperty(FrameworkSettings.TOKEN_PATH.getValue());

        String clientId     = PlatformAdminToken.CLIENT_ID.value;
        String clientSecret = PlatformAdminToken.CLIENT_SECRET.value;

        return RestAssured.given()
                .baseUri(host)
                .basePath(tokenPath)
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .formParam("scope", "admin")
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("access_token");
    }
}
