package testframework.core.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testframework.core.waits.WaitUtils;

public final class ElementActions {
    private ElementActions() {
    }

    public static void click(By locator){
        WebElement element = WaitUtils.waitForClickable(locator);
        element.click();
    }

    public static void type(By locator, String value){
        WebElement element = WaitUtils.waitForVisible(locator);
        element.clear();
        element.sendKeys(value);
    }

    public static boolean isVisible(By locator){
        try {
            WebElement element = WaitUtils.waitForVisible(locator);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
