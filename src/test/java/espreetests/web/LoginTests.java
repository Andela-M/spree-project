package espreetests.web;

import com.spree.api.PlatformApi;
import com.spree.api.User;
import espreetests.helper.UserFields;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testframework.core.BaseWebTest;

@Epic("Spree E-Commerce Web UI")
@Feature("User Authentication")
@Story("Customer Login Flow")
public class LoginTests extends BaseWebTest {

    PlatformApi platformApi = new PlatformApi();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify user login via web UI - create user via API, then test login form submission and success message")
    @Issue("SPREE-WEB-002")
    @TmsLink("TC-WEB-LOGIN-001")
    void customerIsAbleToLogin() {
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

        navigationSection.navigate();
        navigationSection.clickLogin();
        navigationSection.enterLoginEmail(email);
        navigationSection.enterLoginPassword(password);
        navigationSection.clickLoginButton();

        Assertions.assertTrue(navigationSection.isFlashMessageVisible(), "Login success message should be visible");
    }
}
