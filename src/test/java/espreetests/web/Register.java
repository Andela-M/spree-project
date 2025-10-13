package espreetests.web;

import espreetests.helper.UserFields;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testframework.core.BaseWebTest;

public class Register extends BaseWebTest {

    @Test
    void userIsAbleToRegister(){
        String password = UserFields.userPassword();
        navigationSection.navigate();
        navigationSection.clickLogin();
        navigationSection.clickSignUp();
        navigationSection.enterSignUpEmail(UserFields.userEmail());
        navigationSection.enterSignUpPassword(password);
        navigationSection.enterSignUpPasswordConfirmation(password);
        navigationSection.clickSignUpButton();
        Assertions.assertTrue(navigationSection.signUpSuccessMessageVisible());
        System.out.println();
    }
}
