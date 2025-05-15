package com.practicesoftwaretesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TopMenuBar {

    private WebDriver driver;

    @FindBy(css = "[data-test='nav-home']")
    private WebElement homeButton;
    @FindBy(css = "[data-test='nav-categories']")
    private WebElement categoriesButton;
    @FindBy(css = "[data-test='nav-contact']")
    private WebElement contactButton;
    @FindBy(css = "[data-test='nav-sign-in']")
    private WebElement signInButton;
    @FindBy(id = "language")
    private WebElement languageButton;

    public TopMenuBar(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void clickHomeButton() {
        homeButton.click();
    }

    public void clickCategoriesButton() {
        categoriesButton.click();
    }

    public void clickContactButton() {
        contactButton.click();
    }

    public void clickSignInButton() {
        signInButton.click();
    }
}
