package testframework.core.waits;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testframework.DriverManager;

public final class WaitUtils {
    private WaitUtils() {
    }

    private static WebDriverWait driverWait() {
        return DriverManager.getDriver().getDriverWait();
    }

    public static WebElement waitForVisible(By locator) {
       return driverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(By locator) {
        return driverWait().until(ExpectedConditions.elementToBeClickable(locator));
    }
}
