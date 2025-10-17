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
                        .baseUri(getServiceUrl())
                        .accept("application/json")
                        .contentType("application/json")
                        .log().all()
        );
    }

    private static final class UserEnvelope {
        @SerializedName("user") final User user;
        UserEnvelope(User user) { this.user = user; }
    }

    public Response createUser(User userBody) {
        String adminToken = TokenClient.getPlatformToken();

        Response response = request()
                .auth().oauth2(adminToken)
                .body(new UserEnvelope(userBody))
                .when()
                .post("/users");

        System.out.println("CREATE user response status: " + response.getStatusCode());
        System.out.println("CREATE user response body: " + response.asString());

        return response;
    }

    public String createUserAndExtractId(User userBody) {
        Response response = createUser(userBody);
        return response.path("data.id");
    }

    public Response getUserById(String id) {
        String adminToken = TokenClient.getPlatformToken();

        Response response = request()
                .auth().oauth2(adminToken)
                .when()
                .get("/users/" + id);

        System.out.println("GET user response status: " + response.getStatusCode());
        System.out.println("GET user response body: " + response.asString());

        return response;
    }

    public Response updateUser(String id, User userBody) {
        String adminToken = TokenClient.getPlatformToken();

        Response response = request()
                .auth().oauth2(adminToken)
                .body(new UserEnvelope(userBody))
                .when()
                .patch("/users/" + id);

        System.out.println("UPDATE user response status: " + response.getStatusCode());
        System.out.println("UPDATE user response body: " + response.asString());

        return response;
    }

    public Response deleteUser(String id) {
        String adminToken = TokenClient.getPlatformToken();

        Response response = request()
                .auth().oauth2(adminToken)
                .when()
                .delete("/users/" + id);

        System.out.println("DELETE user response status: " + response.getStatusCode());
        System.out.println("DELETE user response body: " + response.asString());

        return response;
    }

}
