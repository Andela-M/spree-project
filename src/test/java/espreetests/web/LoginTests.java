package espreetests.web;

import com.spree.api.PlatformApi;
import com.spree.api.User;
import espreetests.helper.UserFields;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testframework.core.BaseWebTest;

public class LoginTests extends BaseWebTest {

    PlatformApi platformApi = new PlatformApi();

    @Test
    void customerIsAbleToLogin() {
        // Setup: Create user via API
        String email = UserFields.userEmail();
        String password = UserFields.userPassword();

        User userBody = User.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();

        Response createResponse = platformApi.createUser(userBody);
        Assertions.assertEquals(201, createResponse.getStatusCode(), "User should be created successfully");

        System.out.println("Created user with email: " + email);

        // Test: Login via UI
        navigationSection.navigate();
        navigationSection.clickLogin();
        navigationSection.enterLoginEmail(email);
        navigationSection.enterLoginPassword(password);
        navigationSection.clickLoginButton();

        // Verify: Check for login success message
        Assertions.assertTrue(navigationSection.isFlashMessageVisible(), "Login success message should be visible");
    }
}
