package com.spree.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import testframework.core.actions.ElementActions;
import testframework.core.waits.WaitUtils;

public class NavigationSection extends BaseSpreePage {

    protected By loginPageButtonLocator = By.cssSelector("button[data-action*='slideover-account']");
    protected By signUpLocator = By.cssSelector("a[href='/user/sign_up']");
    protected By signUpEmailLocator = By.id("user_email");
    protected By signUpPasswordLocator = By.id("user_password");
    protected By signUpPasswordConfirmLocator = By.id("user_password_confirmation");
    protected By signUpButtonLocator = By.cssSelector("input[value='Sign Up']");
    protected By loginEmailLocator = By.id("user_email");
    protected By loginPasswordLocator = By.id("user_password");
    protected By loginButtonLocator = By.cssSelector("#login-button");
    protected By flashMessage = By.cssSelector("p.flash-message");

    public NavigationSection() {
        super("");
    }

    public void clickLogin() {
        WaitUtils.waitForClickable(loginPageButtonLocator);
        WebElement loginButton = driver().findElement(loginPageButtonLocator);
        JavascriptExecutor js = (JavascriptExecutor) driver().getWebDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", loginButton);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        js.executeScript("arguments[0].click();", loginButton);
    }

    public void clickSignUp() {
        ElementActions.click(signUpLocator);
    }

    public void enterSignUpEmail(String email){
        ElementActions.type(signUpEmailLocator, email);
    }

    public void enterSignUpPassword(String password){
        ElementActions.type(signUpPasswordLocator, password);
    }

    public void enterSignUpPasswordConfirmation(String passwordConfirmation){
        ElementActions.type(signUpPasswordConfirmLocator, passwordConfirmation);
    }

    public void clickSignUpButton() {
        ElementActions.click(signUpButtonLocator);
    }

    public void enterLoginEmail(String email){
        ElementActions.type(loginEmailLocator, email);
    }

    public void enterLoginPassword(String password){
        ElementActions.type(loginPasswordLocator, password);
    }

    public void clickLoginButton() {
        ElementActions.click(loginButtonLocator);
    }

    public boolean isFlashMessageVisible(){
        return ElementActions.isVisible(flashMessage);
    }
}
