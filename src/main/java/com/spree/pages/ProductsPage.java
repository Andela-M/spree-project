package com.spree.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import testframework.DriverManager;
import testframework.core.actions.ElementActions;

public class ProductsPage extends BaseSpreePage{
    public ProductsPage() {
        super("/products");
    }
    Actions actions = new Actions(DriverManager.getDriver());

    private By productsListLocator = By.id("products");
    private By selectSizeLocator = By.cssSelector("#option-2-value");
    private By addToCartButtonLocator = By.cssSelector("div[class='w-full bottom-0 flex flex-col gap-4 z-10'] button[name='button']");
    private By slideOverCartLocator = By.id("slideover-cart");

    public void selectProductByName(String productName){
        try {
            ElementActions.isVisible(productsListLocator);
            By productNameLocator = By.xpath("//h3[normalize-space()='" + productName + "']");
            ElementActions.click(productNameLocator);
        } catch (Exception ex) {
            throw new AssertionError("Product '" + productName + "' is not available. Choose another product!", ex);
        }
    }

    public void clickSelectSize(){
        ElementActions.click(selectSizeLocator);
    }

    public void selectSize(String size){
        try {
            ElementActions.isVisible(By.xpath("//label[starts-with(@class,'text-sm')]/p"));
            By sizeLocator = By.xpath("//label[starts-with(@class,'text-sm')]/p[text()='" + size + "']/parent::label");
            ElementActions.click(sizeLocator);
        } catch (Exception ex) {
            throw new AssertionError("Size '" + size + "' is not available. Choose another size!", ex);
        }
    }

    public void clickAddToCartButton(){
        ElementActions.click(addToCartButtonLocator);
    }

    public boolean slideOverCartIsVisible(){
        return ElementActions.isVisible(slideOverCartLocator);
    }
}
