package espreetests.api;

import com.github.javafaker.Faker;
import com.spree.api.PlatformApi;
import com.spree.api.User;
import espreetests.core.BasePlatformApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserCRUD extends BasePlatformApiTest {
    private final Faker faker = new Faker();
    PlatformApi platformApi = new PlatformApi();

    @Test
    @Feature("User Management")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify user creation via POST /users returns valid user ID")
    void userSuccessfullyCreated() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(6, 128, false, false, false);

        User body = User.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();

        String id = platformApi.createUserAndExtractId(body);

        assertNotNull(id, "User ID should not be null after creation");
    }

    @Test
    @Feature("User Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify GET /users/{id} retrieves created user. FAILS: POST writes to 'spree_users', GET reads from 'spree_admin_users'")
    void userSuccessfullyFetched(){
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(6, 128, false, false, false);

        User body = User.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();

        String id = platformApi.createUserAndExtractId(body);
        System.out.println("Created user ID: " + id);

        Response resp = platformApi.getUserById(id);

        assertEquals(200, resp.getStatusCode(), "GET user should return 200 OK");
        
        String idFetched    = resp.path("data.id");
        String emailFetched = resp.path("data.attributes.email");

        assertEquals(id, idFetched, "Fetched user ID should match created user ID");
        assertEquals(email, emailFetched, "Fetched user email should match created user email");
    }

    @Test
    @Feature("User Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify PATCH /users/{id} updates user. FAILS: PATCH reads from 'spree_admin_users' instead of 'spree_users'")
    void userSuccessfullyUpdated(){
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(6, 128, false, false, false);

        User body = User.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();

        String id = platformApi.createUserAndExtractId(body);
        System.out.println("Created user ID: " + id);

        String newEmail = faker.internet().emailAddress();
        User updateBody = User.builder()
                .email(newEmail)
                .build();

        Response resp = platformApi.updateUser(id, updateBody);

        assertEquals(200, resp.getStatusCode(), "UPDATE user should return 200 OK");
        
        String emailUpdated = resp.path("data.attributes.email");

        assertEquals(newEmail, emailUpdated, "Updated email should match the new email value");
    }

    @Test
    @Feature("User Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify DELETE /users/{id} deletes user. FAILS: DELETE reads from 'spree_admin_users' instead of 'spree_users'")
    void userSuccessfullyDeleted(){
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(6, 128, false, false, false);

        User body = User.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();

        String id = platformApi.createUserAndExtractId(body);
        System.out.println("Created user ID: " + id);

        Response deleteResp = platformApi.deleteUser(id);

        assertEquals(204, deleteResp.getStatusCode(), "DELETE user should return 204 No Content");

        Response getResp = platformApi.getUserById(id);
        assertEquals(404, getResp.getStatusCode(), "Deleted user should return 404 when fetched");
    }
}
