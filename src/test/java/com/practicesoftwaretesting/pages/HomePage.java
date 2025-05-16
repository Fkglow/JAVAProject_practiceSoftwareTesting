package com.practicesoftwaretesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    // SIDE BAR
    @FindBy(css = ".form-select")
    private WebElement sortSelect;
    @FindBy(css = ".ngx-slider-pointer-max")
    private WebElement priceRangeSliderMaxPointer;
    @FindBy(id = "search-query")
    private WebElement searchField;
    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;
    // PRODUCTS
    @FindBy(css = ".col-md-9")
    private WebElement productsList;
    private By product = By.cssSelector(".card");
    private By productName = By.cssSelector(".card-title");
    private By productPrice = By.cssSelector("[data-test='product-price']");
    @FindBy(css = "[data-test='search-caption']")
    private WebElement searchCaption;

    public HomePage(WebDriver driver) {
       this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectSortOptionByIndex(int index) {
        new Select(sortSelect).selectByIndex(index);
    }

    public void enterProductInSearchField(String productName) {
        searchField.sendKeys(productName);
    }

    public void clickSearchProductButton() {
        searchButton.click();
    }

    public List<WebElement> getProductsList() {
        return productsList.findElements(product);
    }

    public ProductPage goToProduct(WebElement product) {
        product.click();
        return new ProductPage(driver);
    }

    public void waitForTableToReload() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getProductsNamesFromTheList(List<WebElement> products) {
        return products.stream().map(this::getProductName).toList();
    }

    public List<Double> getProductsPricesFromTheList(List<WebElement> products) {
        return products.stream().map(this::getProductPrice).toList();
    }

    public String getProductName(WebElement product) {
        return product.findElement(productName).getText();
    }

    public double getProductPrice(WebElement product) {
        String priceStr = product.findElement(productPrice).getText();
        // Delete currency symbol
        String priceNum = priceStr.substring(1);
        return Double.parseDouble(priceNum);
    }

    public String getSearchCaptionText() {
        return new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(searchCaption)).getText();
    }

}
