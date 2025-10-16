package espreetests.core;

import com.spree.pages.NavigationSection;
import com.spree.pages.ProductsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import testframework.DriverManager;
import testframework.core.BaseWebTest;

public class BaseSpreeWebTest extends BaseWebTest {
    protected NavigationSection navigationSection;
    protected ProductsPage  productsPage;


    @BeforeEach
    void setUp() {
        navigationSection = new NavigationSection();
        productsPage = new ProductsPage();
    }

    @AfterEach
    void tearDown() {
        DriverManager.quitDriver();
    }
}

