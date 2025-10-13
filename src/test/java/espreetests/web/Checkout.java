package espreetests.web;

import org.junit.jupiter.api.Test;
import testframework.core.BaseWebTest;

public class Checkout extends BaseWebTest {

    @Test
    void customerIsAbleToLogin() {
        navigationSection.navigate();
        navigationSection.shopAllIsVisible();
        navigationSection.clickShopAll();
    }
}
