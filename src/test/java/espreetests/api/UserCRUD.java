package espreetests.api;

import com.github.javafaker.Faker;
import com.spree.api.PlatformApi;
import com.spree.api.User;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserCRUD {
    private final Faker faker = new Faker();
    PlatformApi platformApi = new PlatformApi();

    @Test
    void customerSuccessfullyCreated() {

        String email = faker.internet().emailAddress();
        String password = faker.internet().password(6, 128, false, false, false);

        User body = User.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();

        String id = platformApi.createUserAndExtractId(body);

        assertNotNull(id, "id should not be null");

    }

    @Test
    void customerSuccessfullyFetched(){
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(6, 128, false, false, false);

        User body = User.builder()
                .email(email)
                .password(password)
                .passwordConfirmation(password)
                .build();

        String id = platformApi.createUserAndExtractId(body);

        PlatformApi api = new PlatformApi();
        Response resp = api.getUserByIdRaw(id);

        String idFetched    = resp.path("data.id");
        String emailFetched = resp.path("data.attributes.email");

        assertEquals(idFetched, id);
        assertEquals(emailFetched, email);

    }
}
