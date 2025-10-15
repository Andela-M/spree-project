package espreetests.api;

import com.github.javafaker.Faker;
import com.spree.api.StoreFrontApi;
import com.spree.api.User;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testframework.core.BaseStorefrontApiTest;


public class CustomerAccountCreation extends BaseStorefrontApiTest {
    private Faker faker =  new Faker();
    private StoreFrontApi storeFrontApi = new StoreFrontApi();

    @Test
    @Feature("Account Creation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify customer account is successfully created via storefront API with valid credentials")
    void accountSuccessfullyCreated(){
        String email = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String password = faker.internet()
                .password(6, 128, false, true, false );

        User body = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .passwordConfirmation(password)
                .build();

        Response response = storeFrontApi.createAccount(body);

        response.then().assertThat().statusCode(200);
        String responseEmail = response.jsonPath().getString("data.attributes.email");

        Assertions.assertEquals(email, responseEmail, "Emails from response does not match!");
    }
}
