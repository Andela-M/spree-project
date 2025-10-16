package espreetests.web;
import espreetests.core.BaseSpreeWebTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddProductsToCartTests extends BaseSpreeWebTest {
    private static final String SIZE_XS = "XS";
    private String pants = "Pants";
    private String oversizedSweatshirt = "Oversize Sweatshirt";

    @Test
    void userCanAddProductsToCart() {
        navigationSection.navigate();
        navigationSection.clickShopAll();
        productsPage.selectProductByName(pants);
        productsPage.clickSelectSize();
        productsPage.selectSize(SIZE_XS);
        productsPage.clickAddToCartButton();

        Assertions.assertTrue(productsPage.slideOverCartIsVisible(), "Product was not added to cart!");
    }
}
