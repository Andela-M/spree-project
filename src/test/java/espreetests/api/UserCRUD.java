package espreetests.api;

import com.github.javafaker.Faker;
import com.spree.api.PlatformApi;
import com.spree.api.User;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import testframework.core.BaseApiTest;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Spree Platform API")
@Feature("User Management")
@Story("User CRUD Operations")
public class UserCRUD extends BaseApiTest {
    private final Faker faker = new Faker();
    PlatformApi platformApi = new PlatformApi();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Purpose: Verify that a new user can be created via Platform API POST /users endpoint")
    @Issue("SPREE-001")
    @TmsLink("TC-USER-001")
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
        assertTrue(id.matches("\\d+"), "User ID should be numeric");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Purpose: Verify that a created user can be retrieved by ID via GET /users/{id}\n\n" +
                 "KNOWN ISSUE: This test currently fails due to Platform API limitation.\n" +
                 "Root Cause: POST creates users in 'spree_users' table, but GET queries 'spree_admin_users' table.\n" +
                 "Expected Result: 200 with user data\n" +
                 "Actual Result: 404 'The resource you were looking for could not be found'\n\n" +
                 "Analysis: The Platform API /users endpoints appear to be designed for Admin User management only, " +
                 "not regular customer users. This is a framework-level issue in Spree Commerce v5.1.7.")
    @Issue("SPREE-002")
    @TmsLink("TC-USER-002")
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
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Purpose: Verify that a user's information can be updated via PATCH /users/{id}\n\n" +
                 "KNOWN ISSUE: This test is expected to fail for the same reason as GET endpoint.\n" +
                 "Root Cause: PATCH endpoint queries 'spree_admin_users' table instead of 'spree_users' table.\n" +
                 "Expected Result: 200 with updated user data\n" +
                 "Actual Result: 404 'The resource you were looking for could not be found'\n\n" +
                 "The user exists in the database but cannot be found by the Platform API PATCH endpoint.")
    @Issue("SPREE-003")
    @TmsLink("TC-USER-003")
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
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Purpose: Verify that a user can be deleted via DELETE /users/{id}\n\n" +
                 "KNOWN ISSUE: This test is expected to fail for the same reason as GET and PATCH endpoints.\n" +
                 "Root Cause: DELETE endpoint queries 'spree_admin_users' table instead of 'spree_users' table.\n" +
                 "Expected Result: 204 No Content (successful deletion)\n" +
                 "Actual Result: 404 'The resource you were looking for could not be found'\n\n" +
                 "Impact: Test data cleanup requires database-level access. " +
                 "For production testing, a database cleanup utility or admin console access would be required.")
    @Issue("SPREE-004")
    @TmsLink("TC-USER-004")
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
