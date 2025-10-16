package espreetests.web;

import espreetests.core.BaseSpreeWebTest;
import espreetests.helper.UserFields;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegisterTests extends BaseSpreeWebTest {

    @Test
    @Feature("User Registration")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify user registration via web UI - user completes sign up form and sees success message")
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
