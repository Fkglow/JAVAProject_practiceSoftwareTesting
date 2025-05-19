package com.practicesoftwaretesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private final Actions move;

    // SIDE BAR
    @FindBy(css = ".form-select")
    private WebElement sortSelect;
    @FindBy(css = ".ngx-slider-pointer-min")
    private WebElement priceRangeSliderMinPointer;
    @FindBy(css = ".ngx-slider-model-value")
    private WebElement priceRangeMinValue;
    @FindBy(css = ".ngx-slider-pointer-max")
    private WebElement priceRangeSliderMaxPointer;
    @FindBy(css = ".ngx-slider-model-high")
    private WebElement priceRangeMaxValue;
    @FindBy(id = "search-query")
    private WebElement searchField;
    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;
    private By categoryFilterCheckboxLabel = By.cssSelector(".checkbox label");
    // PRODUCTS
    @FindBy(css = "div .container")
    private WebElement productsListContainer;
    private By productsListLocator = By.cssSelector(".col-md-9");
    private By product = By.cssSelector(".card");
    private By productName = By.cssSelector(".card-title");
    private By productPrice = By.cssSelector("[data-test='product-price']");
    @FindBy(css = "[data-test='search-caption']")
    private WebElement searchCaption;
    private By paginationList = By.cssSelector(".pagination");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.move = new Actions(driver);
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(productsListContainer));
    }

    public void selectSortOptionByIndex(int index) {
        new Select(sortSelect).selectByIndex(index);
    }

    public void changeMinPrice() {
        move.clickAndHold(priceRangeSliderMinPointer).moveByOffset(70, 0).release().perform();
    }

    public int getMinPriceFromPriceRange() {
        String price = priceRangeMinValue.getText();
        return Integer.parseInt(price);
    }

    public void changeMaxPrice() {
        move.clickAndHold(priceRangeSliderMaxPointer).moveByOffset(-50, 0).release().perform();
    }

    public int getMaxPriceFromPriceRange() {
        String price = priceRangeMaxValue.getText();
        return Integer.parseInt(price);
    }

    public void enterProductInSearchField(String productName) {
        searchField.sendKeys(productName);
    }

    public void clickSearchProductButton() {
        searchButton.click();
    }

    public void selectCategoryCheckboxFilterByLabel(String labelText) {
        List<WebElement> labels = driver.findElements(categoryFilterCheckboxLabel);
        for (WebElement label : labels) {
            if (label.getText().trim().equalsIgnoreCase(labelText.trim())) {
                WebElement checkbox = label.findElement(By.tagName("input"));
                checkbox.click();
                return;
            }
        }
        System.out.println("Label not found" + labelText);
    }

    public List<WebElement> getProductsList() {
        WebElement productsList = driver.findElement(productsListLocator);
        return productsList.findElements(product);
    }

    public ProductPage goToProduct(WebElement product) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(product));
        product.click();
        return new ProductPage(driver);
    }

    public void waitForTableToReload() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitForSortingToComplete() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.attributeContains(productsListContainer, "data-test", "sorting_completed"));
    }

    public void waitForSearchingProductToComplete() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.attributeContains(productsListContainer, "data-test", "search_completed"));
    }

    public void waitForFilteringToComplete() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.attributeContains(productsListContainer, "data-test", "filter_completed"));
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

    public List<String> getAllProductsNames() {
        if (isPaginationDisplayed()) {
            List<WebElement> totalPages = getPagesButtons();
            List<String> allNames = new ArrayList<>();
            for (int i = 1; i <= totalPages.size()-1; i++) {
                totalPages.get(i).click();
                waitForTableToReload();
                List<WebElement> productsOnPage = getProductsList();
                allNames.addAll(getProductsNamesFromTheList(productsOnPage));
            }
            return allNames;
        } else {
            return getProductsNamesFromTheList(getProductsList());
        }
    }

    public double getProductPrice(WebElement product) {
        String priceStr = product.findElement(productPrice).getText();
        // Delete currency symbol
        String priceNum = priceStr.substring(1);
        return Double.parseDouble(priceNum);
    }

    public List<Double> getAllProductsPrices() {
        if (isPaginationDisplayed()) {
            List<WebElement> totalPages = getPagesButtons();
            List<Double> allPrices = new ArrayList<>();
            for (int i = 1; i <= totalPages.size()-1; i++) {
                totalPages.get(i).click();
                waitForTableToReload();
                List<WebElement> productsOnPage = getProductsList();
                allPrices.addAll(getProductsPricesFromTheList(productsOnPage));
            }
            return allPrices;
        } else {
            return getProductsPricesFromTheList(getProductsList());
        }
    }

    public String getSearchCaptionText() {
        return new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(searchCaption)).getText();
    }

    private boolean isPaginationDisplayed() {
        try {
            WebElement pagination = driver.findElement(paginationList);
            return pagination.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private List<WebElement> getPagesButtons() {
        org.openqa.selenium.WebElement pagination = driver.findElement(paginationList);
        List<org.openqa.selenium.WebElement> list = pagination.findElements(By.cssSelector("li a"));
        // Deleting previous and next page buttons
        list.removeFirst();
        list.removeLast();

        return list;
    }

}
