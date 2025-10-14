package com.spree.pages;

import org.openqa.selenium.By;
import testframework.core.actions.ElementActions;

public class NavigationSection extends BaseSpreePage {

    protected By shopAllButtonLocator = By.xpath("//a[contains(@class,'header--nav-link')][.//span[normalize-space()='Shop All']]");
    protected By loginPageButtonLocator = By.cssSelector("button[data-action*='slideover-account#toggle']");
    protected By signUpLocator = By.linkText("Sign Up");
    protected By signUpEmailLocator = By.id("user_email");
    protected By signUpPasswordLocator = By.id("user_password");
    protected By signUpPasswordConfirmLocator = By.id("user_password_confirmation");
    protected By signUpButtonLocator = By.cssSelector("input[value='Sign Up']");
    protected By loginEmailLocator = By.id("user_email");
    protected By loginPasswordLocator = By.id("user_password");
    protected By loginButtonLocator = By.cssSelector("#login-button");
    protected By flashMessage = By.cssSelector("p.flash-message");
    protected By loginModalLocator = By.cssSelector("turbo-frame#login");
    protected By signupFormLocator = By.id("user_email");

    public NavigationSection() {
        super("");
    }

    public boolean isShopAllVisible() {
        return ElementActions.isVisible(shopAllButtonLocator);
    }

    public void clickShopAll() {
        ElementActions.click(shopAllButtonLocator);
    }

    public void clickLogin() {
        ElementActions.click(loginPageButtonLocator);
        ElementActions.isVisible(loginModalLocator); // Wait for modal to appear
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
