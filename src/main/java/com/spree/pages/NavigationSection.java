package com.spree.pages;

import org.openqa.selenium.By;
import testframework.core.actions.ElementActions;

public class NavigationSection extends BaseSpreePage {

    protected By shopAllButtonLocator = By.xpath("//a[contains(@class,'header--nav-link')][.//span[normalize-space()='Shop All']]");
    protected By loginPageButtonLocator = By.cssSelector("div[class='hidden lg:flex'] button[data-action='click->slideover-account#toggle click@window->slideover-account#hide click->toggle-menu#hide touch->toggle-menu#hide'] svg");
    protected By signUpLocator = By.cssSelector("button[data-action*='slideover-account#toggle']");
    protected By signUpEmailLocator = By.id("user_email");
    protected By signUpPasswordLocator = By.id("user_password");
    protected By signUpPasswordConfirmLocator = By.id("user_password_confirmation");
    protected By signUpButtonLocator = By.cssSelector("input[value='Sign Up']");
    protected By loginEmailLocator = By.id("user_email");
    protected By loginPasswordLocator = By.id("user_password");
    protected By loginButtonLocator = By.cssSelector("#login-button");
    protected By successfulSignUpMessage = By.cssSelector("p[class='text-sm lg:text-base font-medium text-center uppercase text-text flash-message']");

    public NavigationSection() {
        super("");
    }

    public void shopAllIsVisible() {
        ElementActions.isVisible(shopAllButtonLocator);
    }

    public void clickShopAll() {
        ElementActions.click(shopAllButtonLocator);
    }

    public void clickLogin() {
        ElementActions.click(loginPageButtonLocator);
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

    public boolean signUpSuccessMessageVisible(){
        try{
            ElementActions.isVisible(successfulSignUpMessage);
            return true;
        } catch (Exception e){
            return false;
        }
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
}
