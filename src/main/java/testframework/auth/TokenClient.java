package testframework.auth;

import io.restassured.RestAssured;
import testframework.PropertiesManager;
import testframework.enums.FrameworkSettings;
import testframework.enums.PlatformAdminToken;

public final class TokenClient {
    private TokenClient() {
    }

    private static String STOREFRONT_TOKEN;
    private static String PLATFORM_TOKEN;

    public static synchronized String getStoreFrontToken(String username, String password) {
        if (STOREFRONT_TOKEN != null) {
            return STOREFRONT_TOKEN;
        }

        String host = PropertiesManager.getConfigProperties()
                .getProperty(FrameworkSettings.BASE_ESPREE_URL.getValue());
        String tokenPath = PropertiesManager.getConfigProperties()
                .getProperty(FrameworkSettings.TOKEN_PATH.getValue());

        STOREFRONT_TOKEN = RestAssured.given()
                .baseUri(host)
                .basePath(tokenPath)
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("access_token");

        return STOREFRONT_TOKEN;
    }

    public static synchronized String getPlatformToken() {
        if (PLATFORM_TOKEN != null) {
            return PLATFORM_TOKEN;
        }

        String host = PropertiesManager.getConfigProperties()
                .getProperty(FrameworkSettings.BASE_ESPREE_URL.getValue());
        String tokenPath = PropertiesManager.getConfigProperties()
                .getProperty(FrameworkSettings.TOKEN_PATH.getValue());

        String clientId = PlatformAdminToken.CLIENT_ID.value;
        String clientSecret = PlatformAdminToken.CLIENT_SECRET.value;

        PLATFORM_TOKEN = RestAssured.given()
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

        return PLATFORM_TOKEN;
    }
}
