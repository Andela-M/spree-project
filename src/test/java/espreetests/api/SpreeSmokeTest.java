package espreetests.api;

import com.github.javafaker.Faker;
import com.spree.api.PlatformApi;
import com.spree.api.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpreeSmokeTest {
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
}
