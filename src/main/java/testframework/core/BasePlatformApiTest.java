package testframework.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import testframework.auth.TokenClient;

public abstract class BasePlatformApiTest {

    @AfterAll
    public static void afterAll() {

    }

    @BeforeAll
    public static void beforeAll() {
        TokenClient.getPlatformToken();
    }
}