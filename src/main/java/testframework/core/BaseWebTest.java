package testframework.core;

import com.spree.pages.NavigationSection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import testframework.Driver;
import testframework.DriverManager;

public abstract class BaseWebTest {

    public NavigationSection navigationSection;

    public static Driver driver() {
        return DriverManager.getDriver();
    }

    public static WebDriverWait driverWait() {
        return driver().getDriverWait();
    }

    @BeforeEach
    void setUp() {
        navigationSection = new NavigationSection();
    }

    @AfterEach
    void tearDown() {
        driver().quit();
    }
}