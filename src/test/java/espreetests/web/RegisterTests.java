package espreetests.web;

import espreetests.helper.UserFields;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testframework.core.BaseWebTest;

@Epic("Spree E-Commerce Web UI")
@Feature("User Registration")
@Story("Customer Sign Up Flow")
public class RegisterTests extends BaseWebTest {

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify user registration via web UI - user completes sign up form and sees success message")
    @Issue("SPREE-WEB-001")
    @TmsLink("TC-WEB-REG-001")
    void userIsAbleToRegister(){
        String password = UserFields.userPassword();
        navigationSection.navigate();
        navigationSection.clickLogin();
        navigationSection.clickSignUp();
        navigationSection.enterSignUpEmail(UserFields.userEmail());
        navigationSection.enterSignUpPassword(password);
        navigationSection.enterSignUpPasswordConfirmation(password);
        navigationSection.clickSignUpButton();
        Assertions.assertTrue(navigationSection.isFlashMessageVisible(), "Sign up success message should be visible");
    }
}
